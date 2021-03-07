package com.example.library.core.common

object AppConstants {
    object DB {
        const val VERSION: Int = 1;
        const val DB_NAME: String = "Library.db"
    }

    object API {
        object BookList {
            const val API_BASE: String = "https://jsonbox.io/";
            const val BOOKS_ENDPOINT: String = "box_479f5c073a80294b4c3b";
        }
    }

    object BookDetail {
        const val BUNDLE_KEY_BOOK_ID = "BOOK_ID"

        object DateFormat {
            const val UTC_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
            const val UTC_TIMEZONE = "UTC"
            const val BOOK_DETAIL_DATE_FORMAT = "MMMM d, yyyy"
        }
    }
}