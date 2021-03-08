package com.example.library.features.deletebook.domain

import com.example.library.features.deletebook.data.DeleteBookLocalRepo
import com.example.library.features.deletebook.data.DeleteBookRemoteRepo
import io.reactivex.Completable
import javax.inject.Inject

class BookDeleter @Inject constructor(
    private val deleteBookRemoteRepo: DeleteBookRemoteRepo,
    private val deleteBookLocalRepo: DeleteBookLocalRepo
) {
    fun deleteBook(id: String) : Completable {
        return deleteBookRemoteRepo.postBookDeletion(id)
            .andThen(deleteBookLocalRepo.deleteBook(id))
    }
}