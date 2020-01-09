package br.com.teste.common.util

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import retrofit2.Response
import java.io.IOException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun <T> Observable<Response<T>>.subscribeTreatment(onNext: (T?) -> Unit, onError: (Throwable) -> Unit): Disposable {
    return this.subscribe({
        if (!it.isSuccessful) {
            onError(Throwable(it.message(), Throwable()))
        } else {
            onNext(it.body())
        }
    }, {
        when (it) {
            is UnknownHostException,
            is SocketTimeoutException,
            is SocketException,
            is IOException ->
                onError(Throwable("Houve um problema ao consultar o servidor. Verifique sua conexão.", Throwable()))
            else ->
                onError(it)
        }
    })
}