package com.example.library.features.deletebook.data

import com.example.library.core.common.AppConstants.API.BookList.DELETE_BOOK_ENDPOINT
import io.reactivex.Completable
import retrofit2.http.DELETE
import retrofit2.http.Path

interface DeleteBookApi {
    @DELETE(DELETE_BOOK_ENDPOINT)
    fun deleteBook(@Path("RECORD_ID") id: String) : Completable
}