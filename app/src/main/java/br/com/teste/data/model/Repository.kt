package br.com.teste.data.model

import com.google.gson.annotations.SerializedName

data class Repository(
    @SerializedName("description")
    val description: String = "",
    @SerializedName("forks_count")
    val forksCount: Int = 0,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("owner")
    val owner: Owner = Owner(),
    @SerializedName("stargazers_count")
    val stargazersCount: Int = 0
)