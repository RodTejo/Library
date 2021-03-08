package com.example.library.features.addbook.data

import com.example.library.features.booklist.data.dao.BookDao
import com.example.library.features.booklist.data.entity.BookEntity
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AddBookLocalRepo @Inject constructor(
    private val bookDao: BookDao
) {
    fun insertBook(bookEntity: BookEntity): Completable {
        return Completable.fromRunnable { bookDao.insertBook(bookEntity) }
            .subscribeOn(Schedulers.io())
    }
}