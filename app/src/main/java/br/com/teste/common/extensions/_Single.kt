package br.com.teste.common.extensions

import br.com.teste.common.util.EspressoIdlingResource
import io.reactivex.Single

fun <T> Single<T>.cacheIdlingResource(): Single<T> {
    EspressoIdlingResource.increment()
    return this.cache()
        .doFinally { if (!EspressoIdlingResource.idlingResource.isIdleNow) EspressoIdlingResource.decrement() }
}