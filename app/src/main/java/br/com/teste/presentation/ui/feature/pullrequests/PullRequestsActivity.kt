package br.com.teste.presentation.ui.feature.pullrequests

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import br.com.teste.R
import br.com.teste.common.extensions.gone
import br.com.teste.common.extensions.show
import br.com.teste.common.extensions.showToast
import br.com.teste.common.util.RequestState
import br.com.teste.data.model.PullRequest
import br.com.teste.presentation.base.BaseActivity
import br.com.teste.presentation.ui.feature.pullrequests.adapter.PullRequestAdapter
import kotlinx.android.synthetic.main.pull_requests_activity.*
import javax.inject.Inject


class PullRequestsActivity : BaseActivity(), PullRequestClickItem {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: PullRequestsViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(PullRequestsViewModel::class.java)
    }

    private val adapter by lazy {
        PullRequestAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pull_requests_activity)


        init()
    }

    private fun init() {
        val creator = intent?.getStringExtra(CREATOR) ?: ""
        val repository = intent?.getStringExtra(REPOSITORY) ?: ""

        supportActionBar?.title = repository
        viewModel.initialize(creator, repository)
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.state.observe(this, Observer { state ->
            when (state) {
                is RequestState.Loading -> {
                    progress_bar.show()
                }

                is RequestState.Error -> {
                    showToast(state.throwable.localizedMessage)
                    hideLoading()
                }

                is RequestState.Success -> hideLoading()

                is RequestState.Empty -> {
                    hideLoading()
                    showToast("This repo dont have pull requests")
                }
            }
        })

        viewModel.pullRequests.observe(this, Observer {
            adapter.items.addAll(it)
            repositoryList.adapter = adapter
            adapter.notifyDataSetChanged()
        })
    }

    private fun hideLoading() {
        progress_bar.gone()
    }


    companion object {

        const val CREATOR = "CREATOR"
        const val REPOSITORY = "REPOSITORY"

        fun createIntent(context: Context, creator: String, repository: String) =
            Intent(context, PullRequestsActivity::class.java).apply {
                putExtra(CREATOR, creator)
                putExtra(REPOSITORY, repository)
            }
    }

    override fun onClickItem(pullRequest: PullRequest) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(pullRequest.htmlUrl)))
    }
}

interface PullRequestClickItem {
    fun onClickItem(pullRequest: PullRequest)
}
