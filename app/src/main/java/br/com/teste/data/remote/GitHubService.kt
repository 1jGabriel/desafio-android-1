package br.com.teste.data.remote

import br.com.teste.data.model.PullRequest
import br.com.teste.data.model.Repositories
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubService {

    @GET("search/repositories")
    fun getRepositories(
        @Query("q") language: String = "language:Java",
        @Query("sort") sort: String = "stars",
        @Query("page") page: Int
    ): Single<Response<Repositories>>

    @GET("repos/{creator}/{repository}/pulls")
    fun getPullRequests(
        @Path("creator") creator: String,
        @Path("repository") repository: String
    ): Single<Response<ArrayList<PullRequest>>>
}