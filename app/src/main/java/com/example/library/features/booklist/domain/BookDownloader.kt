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
                if (it.isEmpty())
                    Completable.error(Throwable())
                else
                    bookListLocalRepo.insertBooks(generateBookEntityList(it))
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
                book.pages
            )
            bookEntityList.add(bookEntity)
        }
        return bookEntityList
    }
}