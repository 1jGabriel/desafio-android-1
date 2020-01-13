package br.com.teste.data.repository

import br.com.teste.common.extensions.cacheIdlingResource
import br.com.teste.common.util.EspressoIdlingResource
import br.com.teste.data.model.PullRequest
import br.com.teste.data.model.Repository
import br.com.teste.data.remote.GitHubService
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class GitHubRepositoryImpl(private val service: GitHubService) : GitHubRepository {
    override fun getRepositories(page: Int): Single<ArrayList<Repository>> {
        return service.getRepositories(page = page)
            .cacheIdlingResource()
            .map { response ->
                when {
                    response.isSuccessful -> {
                        response.body()?.items ?: arrayListOf()
                    }
                    else -> throw Throwable(response.message())
                }
            }
    }

    override fun getPullRequests(creator: String, repository: String): Single<ArrayList<PullRequest>> {
        return service.getPullRequests(creator, repository)
            .subscribeOn(Schedulers.io())
            .cache()
            .map { response ->
                when {
                    response.isSuccessful -> {
                        response.body() ?: arrayListOf()
                    }
                    else -> throw  Throwable(response.message())
                }
            }
    }
}