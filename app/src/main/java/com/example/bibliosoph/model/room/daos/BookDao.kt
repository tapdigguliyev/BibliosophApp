package com.example.bibliosoph.model.room.daos

import androidx.room.*
import com.example.bibliosoph.model.Book
import com.example.bibliosoph.model.room.relations.BookAndGenre

@Dao
interface BookDao {

    @Transaction
    @Query("SELECT * FROM books")
    suspend fun getBooks(): List<BookAndGenre> //TODO: Kotlin Flow may be required in the future

    @Query("SELECT id FROM books")
    suspend fun getBooksIds(): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBook(book: Book)

    @Delete
    suspend fun removeBook(book: Book)

    @Transaction
    @Query("SELECT * FROM books WHERE id = :bookId")
    suspend fun getBookById(bookId: String): BookAndGenre
}