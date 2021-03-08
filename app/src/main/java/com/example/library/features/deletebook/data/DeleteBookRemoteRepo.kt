package com.example.library.features.deletebook.data

import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DeleteBookRemoteRepo @Inject constructor(
    private val deleteBookApi: DeleteBookApi
) {
    fun postBookDeletion(id: String) : Completable {
        return deleteBookApi.deleteBook(id)
            .subscribeOn(Schedulers.io())
    }
}