package br.com.teste.data.repository

import br.com.teste.data.model.Repository
import br.com.teste.data.remote.GitHubService
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class GitHubRepositoryImpl(private val service: GitHubService) : GitHubRepository {
    override fun getRepositories(page: Int): Single<ArrayList<Repository>> {
        return service.getRepositories(page = page)
            .subscribeOn(Schedulers.io())
            .cache()
            .map { response ->
                when {
                    response.isSuccessful -> {
                        response.body()?.items ?: arrayListOf()
                    }
                    else -> throw Throwable(response.message())
                }
            }
    }
}