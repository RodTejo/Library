package com.example.library.core.framework

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel : ViewModel(){
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private val failureLiveData = MutableLiveData<Throwable>();

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    protected fun addDisposable(disposable: Disposable) = compositeDisposable.add(disposable)

    protected fun handleError(throwable: Throwable) = failureLiveData.postValue(throwable)

    protected fun getFailureLiveData() : LiveData<Throwable> = failureLiveData
}