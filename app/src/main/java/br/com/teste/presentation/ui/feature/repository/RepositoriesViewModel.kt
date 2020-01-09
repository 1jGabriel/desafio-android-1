package br.com.teste.presentation.ui.feature.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import br.com.teste.common.util.RequestState
import br.com.teste.data.model.Repository
import br.com.teste.data.repository.GitHubRepository
import br.com.teste.presentation.base.BaseViewModel
import br.com.teste.presentation.ui.feature.repository.paging.RepositoryDataSourceFactory
import br.com.teste.presentation.ui.feature.repository.paging.RepositoryPagedKeyDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class RepositoriesViewModel @Inject constructor(val repository: GitHubRepository) : BaseViewModel() {

    private lateinit var lDataSource: RepositoryPagedKeyDataSource
    private lateinit var lDataSourceFactory: RepositoryDataSourceFactory
    private lateinit var lPagedList: LiveData<PagedList<Repository>>

    private val mutableState = MutableLiveData<RequestState>()
    val state: LiveData<RequestState>
        get() = mutableState

    val pagedList: LiveData<PagedList<Repository>>
        get() = if (::lPagedList.isInitialized) lPagedList else throw IllegalStateException("Call initialize function before use this variable")

    fun onCreate() {
        initDataSourceFactory()
    }

    private fun initDataSourceFactory() {
        lDataSourceFactory = RepositoryDataSourceFactory(
            disposables, repository
        )

        initStateObserver(lDataSourceFactory)

        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setInitialLoadSizeHint(PAGE_SIZE)
            .setEnablePlaceholders(false)
            .build()

        lPagedList = LivePagedListBuilder(lDataSourceFactory, config).build()
    }

    private fun initStateObserver(dataSourceFactory: RepositoryDataSourceFactory) {
        val disposable = dataSourceFactory.dataSource
            .doOnNext { source -> lDataSource = source }
            .flatMap { source -> source.state }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { current -> mutableState.value = current }
        disposables.add(disposable)
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}
