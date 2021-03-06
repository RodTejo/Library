package com.example.library.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.library.core.common.AppConstants.DB.VERSION
import com.example.library.features.booklist.data.dao.BookDao
import com.example.library.features.booklist.data.entity.BookEntity

@Database(entities = [BookEntity::class],
            version = VERSION,
            exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
}