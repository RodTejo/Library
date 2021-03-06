package com.example.library.features.booklist.data.model

import com.google.gson.annotations.SerializedName

data class AuthorModel (
    @SerializedName("first_name")
    val authorName: String,
    @SerializedName("last_name")
    val authorLastName: String
)