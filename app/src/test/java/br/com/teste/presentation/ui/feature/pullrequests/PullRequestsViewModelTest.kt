package br.com.teste.presentation.ui.feature.pullrequests

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.teste.RxAndroidSchedulerRule
import br.com.teste.common.util.RequestState
import br.com.teste.data.model.PullRequest
import br.com.teste.data.repository.GitHubRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class PullRequestsViewModelTest {
    @get:Rule
    val rxAndroidSchedulerRule = RxAndroidSchedulerRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val repository = mockk<GitHubRepository>()

    private lateinit var viewModel: PullRequestsViewModel

    val oneItemResponse = Single.just(arrayListOf(PullRequest()))

    val creator = "CREATOR"

    val repo = "REPO"

    @Before
    fun setUp() {
        viewModel = PullRequestsViewModel(repository)
    }

    @Test
    fun `when viewModel is initialized must call getPullRequests`() {
        every { repository.getPullRequests(creator, repo) } returns oneItemResponse

        viewModel.initialize(creator, repo)

        verify {
            repository.getPullRequests(creator, repo)
        }
    }


    @Test
    fun `when viewModel is initialized must call getPullRequests without errors`() {
        every { repository.getPullRequests(creator, repo) } returns oneItemResponse

        viewModel.initialize(creator, repo)

        assert(viewModel.state.value == RequestState.Success)
        assert(viewModel.pullRequests.value != null)

        verify {
            repository.getPullRequests(creator, repo)
        }
    }

    @Test
    fun `when viewModel is initialized must throw an exception`() {
        val errorResponse = IOException()
        every { repository.getPullRequests(creator, repo) } returns Single.error(errorResponse)

        viewModel.initialize(creator, repo)

        assert(viewModel.state.value == RequestState.Error(errorResponse))
        assert(viewModel.pullRequests.value == null)

        verify {
            repository.getPullRequests(creator, repo)
        }
    }


}