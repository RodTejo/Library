package com.example.library.features.deletebook.data

import com.example.library.features.booklist.data.dao.BookDao
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DeleteBookLocalRepo @Inject constructor(
    private val bookDao: BookDao
) {
    fun deleteBook(id: String): Completable {
        return Completable.fromRunnable { bookDao.deleteBook(id) }
            .subscribeOn(Schedulers.io())
    }
}