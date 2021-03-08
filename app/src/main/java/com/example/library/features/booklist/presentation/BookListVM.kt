package com.example.library.features.booklist.presentation

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.library.R
import com.example.library.core.framework.BaseViewModel
import com.example.library.features.booklist.BookDownloadStates
import com.example.library.features.booklist.BookListNetworkException
import com.example.library.features.booklist.BookListStorageException
import com.example.library.features.booklist.data.BookListLocalRepo
import com.example.library.features.booklist.data.entity.BookEntity
import com.example.library.features.booklist.domain.BookDownloader
import com.example.library.features.deletebook.DeleteBookNetworkException
import com.example.library.features.deletebook.domain.BookDeleter
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class BookListVM @Inject constructor(
    private val bookListLocalRepo: BookListLocalRepo,
    private val bookDownloader: BookDownloader,
    private val bookDeleter: BookDeleter,
    private val application: Application
): BaseViewModel() {
    private val booksListLiveData = MutableLiveData<List<BookEntity>>()
    private val booksDownloadStateLiveData = MutableLiveData<BookDownloadStates>()

    var bookId = String()

    fun initObservers() {
        addDisposable(
            bookListLocalRepo.getBooks()
                .doOnError { throw BookListStorageException(application.getString(R.string.storage_error_message)) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(booksListLiveData::setValue, this::handleError)
        )
    }

    fun downLoadBooks() {
        addDisposable(
            bookDownloader.downloadBooks()
                .doOnSubscribe { booksDownloadStateLiveData.postValue(BookDownloadStates.DOWNLOAD_INIT) }
                .doOnError { throw BookListNetworkException(application.getString(R.string.network_error_message)) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({booksDownloadStateLiveData.postValue(BookDownloadStates.DOWNLOAD_SUCCESS)},
                    this::handleError)
        )
    }

    fun deleteBook() {
        if (bookId.isEmpty())
            return

        addDisposable(
            bookDeleter.deleteBook(bookId)
                .doOnSubscribe { booksDownloadStateLiveData.postValue(BookDownloadStates.DOWNLOAD_INIT) }
                .doOnError { throw DeleteBookNetworkException(application.getString(R.string.delete_book_network_error)) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({booksDownloadStateLiveData.postValue(BookDownloadStates.DOWNLOAD_SUCCESS)},
                    this::handleError)
        )
    }

    fun getBooksLiveData(): LiveData<List<BookEntity>> = booksListLiveData

    fun getDownloadStateLiveData(): LiveData<BookDownloadStates> = booksDownloadStateLiveData
}