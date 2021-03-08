package com.example.library.core.di.storage

import android.app.Application
import androidx.room.Room
import com.example.library.core.common.AppConstants.DB.DB_NAME
import com.example.library.core.db.AppDatabase
import com.example.library.features.booklist.data.dao.BookDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule {
    @Provides
    @Singleton
    internal fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, DB_NAME)
            .allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    internal fun provideBookDao(appDatabase: AppDatabase): BookDao {
        return appDatabase.bookDao()
    }
}