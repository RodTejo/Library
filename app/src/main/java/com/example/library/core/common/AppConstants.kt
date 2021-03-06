package com.example.library.core.common

object AppConstants {
    object DB {
        const val VERSION: Int = 1;
        const val DB_NAME: String = "Library.db"
    }

    object API {
        object BookList {
            const val API_BASE: String = "https://jsonbox.io/";
            const val GET_BOOKS: String = "box_479f5c073a80294b4c3b";
        }
    }
}