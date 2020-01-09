package br.com.teste.presentation.ui.feature.repository.paging

import androidx.paging.DataSource
import br.com.teste.data.model.Repository
import br.com.teste.data.repository.GitHubRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

class RepositoryDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val gitHubRepository: GitHubRepository
) : DataSource.Factory<Int, Repository>() {

    val dataSource: Subject<RepositoryPagedKeyDataSource> = PublishSubject.create()

    override fun create(): DataSource<Int, Repository> {
        val mDataSource = RepositoryPagedKeyDataSource(
            compositeDisposable = compositeDisposable, gitHubRepository = gitHubRepository
        )

        dataSource.onNext(mDataSource)

        return mDataSource
    }
}