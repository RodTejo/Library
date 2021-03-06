package com.example.library.features.booklist.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.library.features.booklist.data.entity.BookEntity
import io.reactivex.Flowable

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBooks(books: List<BookEntity>)

    @Query("SELECT * FROM BookEntity")
    fun getBooks(): Flowable<List<BookEntity>>
}