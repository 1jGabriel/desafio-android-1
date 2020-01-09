package br.com.teste.common.util

sealed class RequestState {
    data class Error(val throwable: Throwable) : RequestState()
    object Loading : RequestState()
    object Success : RequestState()
    object Empty : RequestState()
}