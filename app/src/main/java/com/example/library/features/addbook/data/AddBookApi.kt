package com.example.library.features.addbook.data

import com.example.library.core.common.AppConstants.API.BookList.BOOKS_ENDPOINT
import com.example.library.features.booklist.data.model.BookModel
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface AddBookApi {
    @POST(BOOKS_ENDPOINT)
    fun postBook(@Body bookModel: BookModel): Single<BookModel>
}