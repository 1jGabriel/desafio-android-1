package br.com.teste.presentation.ui.feature.repository

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import br.com.teste.R
import br.com.teste.common.extensions.gone
import br.com.teste.common.extensions.show
import br.com.teste.common.extensions.showToast
import br.com.teste.common.util.RequestState
import br.com.teste.data.model.Repository
import br.com.teste.presentation.base.BaseActivity
import br.com.teste.presentation.ui.feature.pullrequests.PullRequestsActivity
import br.com.teste.presentation.ui.feature.repository.adapter.RepositoryAdapter
import kotlinx.android.synthetic.main.repositories_activity.*
import javax.inject.Inject

class RepositoriesActivity : BaseActivity(), RepositoryClickItem {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: RepositoriesViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(RepositoriesViewModel::class.java)
    }

    private val adapter by lazy {
        RepositoryAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.repositories_activity)

        init()
    }

    private fun init() {
        setupRecyclerView()
        viewModel.onCreate()
        setupObservers()
    }

    private fun setupRecyclerView() {
        repositoryList.adapter = adapter
    }

    private fun setupObservers() {

        viewModel.state.observe(this, Observer { state ->
            when (state) {
                is RequestState.Loading -> {
                    progress_bar.show()
                }

                is RequestState.Error -> {
                    hideLoading()
                    showToast(state.throwable.localizedMessage)
                }

                is RequestState.Success -> hideLoading()

                is RequestState.Empty -> {
                    hideLoading()
                    showToast("We dont have repositories to show now")
                }
            }
        })

        viewModel.pagedList.observe(this, Observer<PagedList<Repository>> {
            adapter.submitList(it)

        })
    }

    private fun hideLoading() {
        progress_bar.gone()
    }

    override fun onClick(repository: Repository) {
        startActivity(
            PullRequestsActivity.createIntent(
                context = this,
                repository = repository.name,
                creator = repository.owner.login
            )
        )
    }
}

interface RepositoryClickItem {
    fun onClick(repository: Repository)
}