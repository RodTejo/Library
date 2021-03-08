package com.example.library.features.booklist.data.model

import com.google.gson.annotations.SerializedName

data class BookModel (
    @SerializedName("_id")
    var _id: String,
    @SerializedName("isbn")
    var isbn: String,
    @SerializedName("title")
    var title: String,
    @SerializedName("category")
    var category: String,
    @SerializedName("pages")
    var pages: Long,
    @SerializedName("description")
    var description: String,
    @SerializedName("image_url")
    var imageUrl: String,
    @SerializedName("author")
    var author: AuthorModel,
    @SerializedName("_createdOn")
    var creationDate: String,
    @SerializedName("published")
    var publisher: String
) {
    constructor() : this("","", "", "", -1, "",
    "" , AuthorModel(), "", "")
}
