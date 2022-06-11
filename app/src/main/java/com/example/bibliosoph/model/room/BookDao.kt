package com.example.bibliosoph.model.room

import androidx.room.*
import com.example.bibliosoph.model.Book
import com.example.bibliosoph.model.room.relations.BookAndGenre

@Dao
interface BookDao {

    @Query("SELECT * FROM books")
    fun getBooks(): List<BookAndGenre>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBook(book: Book)

    @Delete
    fun removeBook(book: Book)

    @Query("SELECT * FROM books WHERE id = :bookId")
    fun getBookById(bookId: String): BookAndGenre
}