package com.example.library.features.booklist.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.library.features.booklist.data.entity.BookEntity
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBooks(books: List<BookEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBook(book: BookEntity)

    @Query("SELECT * FROM BookEntity")
    fun getBooks(): Flowable<List<BookEntity>>

    @Query("SELECT * FROM BookEntity WHERE id = :id")
    fun getBookDetail(id: String): Single<BookEntity>

    @Query("DELETE FROM BookEntity WHERE id = :id")
    fun deleteBook(id: String)
}