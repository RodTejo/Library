package com.example.library.core.framework

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjection
import javax.inject.Inject

open class BaseActivity : AppCompatActivity(){
    @Inject
    lateinit var factory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
    }

    protected fun <T: ViewModel> getViewModel(tClass: Class<T>): T {
        return ViewModelProvider(viewModelStore, factory).get(tClass)
    }
}