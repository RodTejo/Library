package com.example.library.features.booklist.data

import com.example.library.core.common.AppConstants.API.BookList.GET_BOOKS
import com.example.library.features.booklist.data.model.BookModel
import io.reactivex.Single
import retrofit2.http.GET

interface BookListApi {
    @GET(GET_BOOKS)
    fun downloadBooks(): Single<List<BookModel>>
}