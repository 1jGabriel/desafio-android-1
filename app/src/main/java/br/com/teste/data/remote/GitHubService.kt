package br.com.teste.data.remote

import br.com.teste.data.model.Repositories
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubService {

    @GET("search/repositories")
    fun getRepositories(
        @Query("q") language: String = "language:Java",
        @Query("sort") sort: String = "stars",
        @Query("page") page: Int
    ): Single<Response<Repositories>>
}