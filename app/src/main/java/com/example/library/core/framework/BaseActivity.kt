package com.example.library.core.framework

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.library.R
import com.example.library.core.utils.GenericUtility
import dagger.android.AndroidInjection
import io.reactivex.exceptions.CompositeException
import javax.inject.Inject

open class BaseActivity : AppCompatActivity(){
    @Inject
    lateinit var factory: ViewModelProvider.Factory

    @Inject
    lateinit var genericUtility: GenericUtility

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
    }

    protected fun <T: ViewModel> getViewModel(tClass: Class<T>): T {
        return ViewModelProvider(viewModelStore, factory).get(tClass)
    }

    protected open fun handleError(throwable: Throwable?) {
        val errorMessage = if (throwable is CompositeException) {
            val stringBuilder = StringBuilder()
            for (exception in throwable.exceptions) {
                stringBuilder.append(exception.message ?: "")
                    .append("\n")
            }
            stringBuilder.toString()
        } else {
            throwable?.message ?: getString(R.string.unknown_error)
        }
        genericUtility.showMessage(errorMessage, this)
    }
}