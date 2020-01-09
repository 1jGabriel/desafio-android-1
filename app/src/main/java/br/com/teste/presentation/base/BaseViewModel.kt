package br.com.teste.presentation.base

import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.teste.data.remote.GitHubService
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseViewModel : ViewModel(), LifecycleObserver {

    var disposables = CompositeDisposable()

    @Inject
    lateinit var service: GitHubService

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
        Log.d("ViewModel", "onCleared")
    }

}