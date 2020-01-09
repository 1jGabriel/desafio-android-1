package br.com.teste.presentation.ui.feature.repository.paging

import br.com.teste.common.util.RequestState
import br.com.teste.data.model.Repository
import br.com.teste.data.repository.GitHubRepository
import br.com.teste.presentation.base.BasePageKeyedDataSource
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

class RepositoryPagedKeyDataSource(
    private val gitHubRepository: GitHubRepository,
    private val compositeDisposable: CompositeDisposable
) : BasePageKeyedDataSource<Repository>() {

    val state: Subject<RequestState> = PublishSubject.create()
    private var retry: Completable? = null

    override fun getData(
        actualPage: Int,
        adjacentPage: Int,
        initialCallback: LoadInitialCallback<Int, Repository>?,
        callback: LoadCallback<Int, Repository>?,
        loadSize: Int
    ) {
        compositeDisposable.add(
            gitHubRepository.getRepositories(actualPage)
                .doOnSubscribe { state.onNext(RequestState.Loading) }
                .subscribe({ response ->
                    callback?.onResult(response, adjacentPage)
                    initialCallback?.onResult(response, null, adjacentPage)
                    if (actualPage == 1 && response.isEmpty()) {
                        state.onNext(RequestState.Empty)
                    }
                    state.onNext(RequestState.Success)
                }, { error ->
                    retry = Completable.fromAction {
                        getData(
                            actualPage, adjacentPage, initialCallback, callback,
                            loadSize
                        )
                    }
                    state.onNext(RequestState.Error(error))
                })
        )
    }

    fun retry() {
        if (retry != null) {
            val disposable = retry!!.subscribe()
            compositeDisposable.add(disposable)
        }
    }
}


