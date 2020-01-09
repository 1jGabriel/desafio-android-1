package br.com.teste.presentation.ui.feature.repository.paging

import androidx.paging.PageKeyedDataSource
import br.com.teste.common.util.RequestState
import br.com.teste.data.model.Repository
import br.com.teste.data.repository.GitHubRepository
import io.mockk.*
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import org.junit.Before
import org.junit.Test

class RepositoryPagedKeyDataSourceTest {
    private val repository = mockk<GitHubRepository>()

    private val compositeDisposable = mockk<CompositeDisposable>()

    private val dataSource = RepositoryPagedKeyDataSource(
        gitHubRepository = repository,
        compositeDisposable = compositeDisposable
    )
    @Before
    fun setup() {
        every { compositeDisposable.add(any()) } returns true
    }

    @Test
    fun `should emit a loading state and an error after on initial load when fetching data`() {
        every { repository.getRepositories(any()) } returns Single.error(Exception())

        val loadInitialParams = mockk<PageKeyedDataSource.LoadInitialParams<Int>>()
        val loadInitialCallback = mockk<PageKeyedDataSource.LoadInitialCallback<Int, Repository>>()

        val state = dataSource.state.test()

        dataSource.loadInitial(loadInitialParams, loadInitialCallback)

        state
            .assertNoErrors()
            .assertValueCount(2)
            .assertValueAt(0) { value -> value is RequestState.Loading }
            .assertValueAt(1) { value -> value is RequestState.Error }
    }

    @Test
    fun `should emit on error return nothing when load repositories data only`() {
        every { repository.getRepositories(any()) } returns Single.error(Exception())

        val loadInitialParams = mockk<PageKeyedDataSource.LoadInitialParams<Int>>()
        val loadInitialCallback = mockk<PageKeyedDataSource.LoadInitialCallback<Int, Repository>>()

        every { loadInitialCallback.onResult(any(), any(), any()) } just Runs

        val state = dataSource.state.test()

        dataSource.loadInitial(loadInitialParams, loadInitialCallback)

        state
            .assertNoErrors()
            .assertValueCount(2)
            .assertValueAt(0) { value -> value is RequestState.Loading }
            .assertValueAt(1) { value -> value is RequestState.Error }

        verify { repository.getRepositories(any()) wasNot called }

        // wasNot called not working with LoadInitialCallback mock :/
        verify(exactly = 0) { loadInitialCallback.onResult(any(), any(), any()) }
    }

    @Test
    fun `should emit error on retry called try to load items again`() {
        every { repository.getRepositories(any()) } returns Single.error(Exception())

        val loadInitialParams = mockk<PageKeyedDataSource.LoadInitialParams<Int>>()
        val loadInitialCallback = mockk<PageKeyedDataSource.LoadInitialCallback<Int, Repository>>()

        val state = dataSource.state.test()

        dataSource.loadInitial(loadInitialParams, loadInitialCallback)

        dataSource.retry()

        state
            .assertNoErrors()
            .assertValueCount(4)
            .assertValueAt(0) { value -> value is RequestState.Loading }
            .assertValueAt(1) { value -> value is RequestState.Error }
            .assertValueAt(2) { value -> value is RequestState.Loading }
            .assertValueAt(3) { value -> value is RequestState.Error }

        verify(exactly = 2) { repository.getRepositories(any()) }
        verify(exactly = 3) { compositeDisposable.add(any()) }
    }

    @Test
    fun `should emit a loading, empty, success state on try to get data`() {
        every { repository.getRepositories(any()) } returns
                Single.just(arrayListOf<Repository>())

        val loadInitialParams = mockk<PageKeyedDataSource.LoadInitialParams<Int>>()
        val loadInitialCallback = mockk<PageKeyedDataSource.LoadInitialCallback<Int, Repository>>()

        every { loadInitialCallback.onResult(any(), any(), any()) } just Runs

        val state = dataSource.state.test()

        dataSource.loadInitial(loadInitialParams, loadInitialCallback)

        state
            .assertNoErrors()
            .assertValueCount(3)
            .assertValueAt(0) { value -> value is RequestState.Loading }
            .assertValueAt(1) { value -> value is RequestState.Empty }
            .assertValueAt(2) { value -> value is RequestState.Success }

        verify(exactly = 1) { loadInitialCallback.onResult(any(), any(), any()) }
    }
}