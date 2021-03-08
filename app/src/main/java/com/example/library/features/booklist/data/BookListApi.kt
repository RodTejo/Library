package com.example.library.features.booklist.data

import com.example.library.core.common.AppConstants.API.BookList.BOOKS_ENDPOINT
import com.example.library.features.booklist.data.model.BookModel
import io.reactivex.Single
import retrofit2.http.GET

interface BookListApi {
    @GET(BOOKS_ENDPOINT)
    fun downloadBooks(): Single<List<BookModel>>
}