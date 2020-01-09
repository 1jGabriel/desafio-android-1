package br.com.teste.data.model


import com.google.gson.annotations.SerializedName

data class Repositories(
    @SerializedName("items")
    val items: ArrayList<Repository> = arrayListOf()
)