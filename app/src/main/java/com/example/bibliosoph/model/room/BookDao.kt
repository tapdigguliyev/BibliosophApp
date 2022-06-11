package com.example.bibliosoph.model.room

import androidx.room.*
import com.example.bibliosoph.model.Book
import com.example.bibliosoph.model.room.relations.BookAndGenre
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @Query("SELECT * FROM books")
    fun getBooks(): Flow<List<BookAndGenre>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBook(book: Book)

    @Delete
    suspend fun removeBook(book: Book)

    @Query("SELECT * FROM books WHERE id = :bookId")
    suspend fun getBookById(bookId: String): BookAndGenre
}