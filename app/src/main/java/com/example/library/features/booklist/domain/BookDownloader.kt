package com.example.library.features.booklist.domain

import com.example.library.core.utils.toEntity
import com.example.library.features.booklist.data.BookListLocalRepo
import com.example.library.features.booklist.data.BookListRemoteRepo
import com.example.library.features.booklist.data.entity.BookEntity
import com.example.library.features.booklist.data.model.BookModel
import io.reactivex.Completable
import javax.inject.Inject

class BookDownloader @Inject constructor(
    private val bookListLocalRepo: BookListLocalRepo,
    private val bookListRemoteRepo: BookListRemoteRepo
) {
    fun downloadBooks() : Completable {
        return bookListRemoteRepo.downLoadBooks()
            .flatMapCompletable {
                if (it.isNotEmpty())
                    bookListLocalRepo.insertBooks(generateBookEntityList(it))
                else
                    Completable.complete()
            }
    }

    private fun generateBookEntityList(bookList: List<BookModel>) : List<BookEntity> {
        val bookEntityList: ArrayList<BookEntity> = ArrayList()
        for (book in bookList) {
            bookEntityList.add(book.toEntity())
        }
        return bookEntityList
    }
}