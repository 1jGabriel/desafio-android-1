package br.com.teste.common.di.builder

import br.com.teste.common.di.annotation.ActivityScope
import br.com.teste.presentation.ui.feature.pullrequests.PullRequestsActivity
import br.com.teste.presentation.ui.feature.repository.RepositoriesActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ActivityScope
    @ContributesAndroidInjector
    abstract fun provideRepositoriesActivity(): RepositoriesActivity

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun providePullRequestsActivity(): PullRequestsActivity
}