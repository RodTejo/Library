package com.example.library.features.booklist.data

import com.example.library.features.booklist.data.model.BookModel
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookListRemoteRepo @Inject constructor(
    private val bookListApi: BookListApi
){
    fun downLoadBooks(): Single<List<BookModel>> {
        return bookListApi.downloadBooks()
            .subscribeOn(Schedulers.io())
    }
}