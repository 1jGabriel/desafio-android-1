package br.com.teste.common.di.builder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.teste.common.di.annotation.ViewModelKey
import br.com.teste.common.di.factory.ViewModelFactory
import br.com.teste.presentation.ui.feature.pullrequests.PullRequestsViewModel
import br.com.teste.presentation.ui.feature.repository.RepositoriesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelBuilder {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(RepositoriesViewModel::class)
    abstract fun bindRepositoriesViewModel(repositoriesViewModel: RepositoriesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PullRequestsViewModel::class)
    abstract fun bindPullRequestsViewModel(pullRequestsViewModel: PullRequestsViewModel): ViewModel


}