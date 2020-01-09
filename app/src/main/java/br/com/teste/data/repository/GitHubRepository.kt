package br.com.teste.data.repository

import br.com.teste.data.model.PullRequest
import br.com.teste.data.model.Repository
import io.reactivex.Single

interface GitHubRepository {
    fun getRepositories(page: Int): Single<ArrayList<Repository>>
    fun getPullRequests(creator: String, repository: String): Single<ArrayList<PullRequest>>
}