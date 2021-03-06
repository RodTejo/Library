package com.example.library.features.booklist.data

import com.example.library.features.booklist.data.dao.BookDao
import com.example.library.features.booklist.data.entity.BookEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookListLocalRepo @Inject constructor(
    private val bookDao: BookDao
) {
    fun insertBooks(books: List<BookEntity>): Completable {
        return Completable.fromRunnable { bookDao.insertBooks(books) }
            .subscribeOn(Schedulers.io())
    }

    fun getBooks(): Flowable<List<BookEntity>> {
        return bookDao.getBooks()
            .subscribeOn(Schedulers.io());
    }
}