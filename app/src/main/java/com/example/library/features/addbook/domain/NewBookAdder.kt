package com.example.library.features.addbook.domain

import com.example.library.core.utils.toEntity
import com.example.library.features.addbook.data.AddBookLocalRepo
import com.example.library.features.addbook.data.AddBookRemoteRepo
import com.example.library.features.booklist.data.model.BookModel
import io.reactivex.Completable
import javax.inject.Inject

class NewBookAdder @Inject constructor(
    private val addBookRemoteRepo: AddBookRemoteRepo,
    private val addBookLocalRepo: AddBookLocalRepo
) {
    fun addBook(bookModel: BookModel) : Completable {
        return addBookRemoteRepo.postBook(bookModel)
            .flatMapCompletable {
                /**
                 * Quick fix addressing a possible issue with API returning current date always
                 * on field _createdOn TODO: review this
                 */
                if (it.creationDate != bookModel.creationDate){
                    it.creationDate = bookModel.creationDate
                }
                addBookLocalRepo.insertBook(it.toEntity())
            }
    }
}