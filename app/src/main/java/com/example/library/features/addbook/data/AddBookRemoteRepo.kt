package com.example.library.features.addbook.data

import com.example.library.features.booklist.data.model.BookModel
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AddBookRemoteRepo @Inject constructor(
    private val addBookApi: AddBookApi
) {
    fun postBook(bookModel: BookModel): Single<BookModel> {
        return addBookApi.postBook(bookModel)
            .subscribeOn(Schedulers.io())
    }
}