package com.example.library.features.bookdetail.data

import com.example.library.features.booklist.data.dao.BookDao
import com.example.library.features.booklist.data.entity.BookEntity
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookDetailLocalRepo @Inject constructor(
    private val bookDao: BookDao
) {
    fun getBookDetail(id: String): Single<BookEntity> {
        return bookDao.getBookDetail(id)
            .subscribeOn(Schedulers.io());
    }
}