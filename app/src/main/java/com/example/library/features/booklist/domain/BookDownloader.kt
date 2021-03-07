package com.example.library.features.booklist.domain

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

    fun generateBookEntityList(bookList: List<BookModel>) : List<BookEntity> {
        val bookEntityList: ArrayList<BookEntity> = ArrayList()
        for (book in bookList) {
            val bookEntity: BookEntity = BookEntity(
                book._id,
                book.isbn,
                book.title,
                book.category,
                book.description,
                book.imageUrl,
                book.author.authorName,
                book.author.authorLastName,
                book.creationDate,
                book.publisher,
                book.pages
            )
            bookEntityList.add(bookEntity)
        }
        return bookEntityList
    }
}