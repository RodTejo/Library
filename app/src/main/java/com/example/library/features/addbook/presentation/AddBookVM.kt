package com.example.library.features.addbook.presentation

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.library.R
import com.example.library.core.framework.BaseViewModel
import com.example.library.features.addbook.AddBookNetworkException
import com.example.library.features.addbook.domain.NewBookAdder
import com.example.library.features.booklist.BookDownloadStates
import com.example.library.features.booklist.data.model.BookModel
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class AddBookVM @Inject constructor(
    private val newBookAdder: NewBookAdder,
    private val application: Application
) : BaseViewModel() {

    private val bookAdditionStateLiveData = MutableLiveData<BookDownloadStates>()
    private var newBook = BookModel()

    fun addBook() {
        addDisposable(
            newBookAdder.addBook(newBook)
                .doOnSubscribe { bookAdditionStateLiveData.postValue(BookDownloadStates.DOWNLOAD_INIT) }
                .doOnError { throw AddBookNetworkException(application.getString(R.string.add_book_network_error)) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({bookAdditionStateLiveData.postValue(BookDownloadStates.DOWNLOAD_SUCCESS)},
                    this::handleError)
        )
    }

    fun getBookAdditionStateLiveData(): LiveData<BookDownloadStates> = bookAdditionStateLiveData

    fun getNewBook() = newBook
}