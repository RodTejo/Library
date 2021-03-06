package com.example.library.features.booklist.data.entity

import androidx.room.Entity

@Entity(primaryKeys = ["id"])
data class BookEntity (
    var id: String,
    val isbn: String,
    val title: String,
    val category: String,
    val description: String,
    val imageUrl: String,
    val authorFirstName: String,
    val authorLastName: String,
    val creationDate: String,
    val pages: Long
)