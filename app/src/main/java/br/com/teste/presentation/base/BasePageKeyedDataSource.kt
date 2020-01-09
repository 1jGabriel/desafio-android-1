package br.com.teste.presentation.base

import androidx.paging.PageKeyedDataSource

abstract class BasePageKeyedDataSource<T> : PageKeyedDataSource<Int, T>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, T>) {
        getData(1, 2, callback, null, params.requestedLoadSize)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        val page = params.key
        getData(page, page + 1, null, callback, params.requestedLoadSize)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        val page = params.key
        getData(page, page - 1, null, callback, params.requestedLoadSize)
    }

    abstract fun getData(
        actualPage: Int,
        adjacentPage: Int,
        initialCallback: LoadInitialCallback<Int, T>?,
        callback: LoadCallback<Int, T>?,
        loadSize: Int
    )
}