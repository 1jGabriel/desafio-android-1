package br.com.teste.data.model

import com.google.gson.annotations.SerializedName

data class Owner(
    @SerializedName("avatar_url")
    val avatarUrl: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("login")
    val login: String = ""
)