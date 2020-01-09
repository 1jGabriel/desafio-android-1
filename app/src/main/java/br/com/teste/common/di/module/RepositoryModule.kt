package br.com.teste.common.di.module

import br.com.teste.data.remote.GitHubService
import br.com.teste.data.repository.GitHubRepository
import br.com.teste.data.repository.GitHubRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun providesRepository(service: GitHubService): GitHubRepository {
        return GitHubRepositoryImpl(service)
    }
}