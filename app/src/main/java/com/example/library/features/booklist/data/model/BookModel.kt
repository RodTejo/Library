package com.example.library.features.booklist.data.model

import com.google.gson.annotations.SerializedName

data class BookModel (
    @SerializedName("_id")
    val _id: String,
    @SerializedName("isbn")
    val isbn: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("pages")
    val pages: Long,
    @SerializedName("description")
    val description: String,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("author")
    val author: AuthorModel,
    @SerializedName("_createdOn")
    val creationDate: String
)
