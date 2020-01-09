package br.com.teste.data.model


import com.google.gson.annotations.SerializedName

data class PullRequest(
    @SerializedName("body")
    val body: String = "",
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("html_url")
    val htmlUrl: String = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("url")
    val url: String = "",
    @SerializedName("user")
    val user: User = User()
)