package br.com.teste.presentation.ui.feature.pullrequests

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.teste.common.util.RequestState
import br.com.teste.data.model.PullRequest
import br.com.teste.data.repository.GitHubRepository
import br.com.teste.presentation.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class PullRequestsViewModel @Inject constructor(val gitHubRepository: GitHubRepository) : BaseViewModel() {
    private val mutableState = MutableLiveData<RequestState>()
    val state: LiveData<RequestState>
        get() = mutableState

    var pullRequests = MutableLiveData<ArrayList<PullRequest>>()

    fun initialize(creator: String, repository: String) {
        disposables.add(
            gitHubRepository.getPullRequests(creator, repository)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showLoading() }
                .doOnSuccess { handleSuccess(it) }
                .doOnError { handleError(it) }
                .subscribe()
        )
    }

    private fun handleError(throwable: Throwable) {
        mutableState.postValue(RequestState.Error(throwable))
    }

    private fun handleSuccess(pullRequests: ArrayList<PullRequest>) {
        this.pullRequests.value = pullRequests
    }

    private fun showLoading() {
        mutableState.value = RequestState.Loading
    }
}
