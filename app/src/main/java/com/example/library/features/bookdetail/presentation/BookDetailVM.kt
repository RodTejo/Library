package com.example.library.features.bookdetail.presentation

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.library.R
import com.example.library.core.framework.BaseViewModel
import com.example.library.features.bookdetail.BookDetailStorageException
import com.example.library.features.bookdetail.data.BookDetailLocalRepo
import com.example.library.features.booklist.data.entity.BookEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class BookDetailVM @Inject constructor(
    private val bookDetailLocalRepo: BookDetailLocalRepo,
    private val application: Application
): BaseViewModel(){
    private val bookDetailLiveData = MutableLiveData<BookEntity>()
    internal var bookId: String? = String()

    fun getBookDetail() {
        addDisposable(
            bookDetailLocalRepo.getBookDetail(bookId?:"")
                .doOnError{ throw BookDetailStorageException(application.getString(R.string.storage_error_message)) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bookDetailLiveData::setValue, this::handleError)
        )
    }

    fun getBookDetailLiveData(): LiveData<BookEntity> = bookDetailLiveData
}