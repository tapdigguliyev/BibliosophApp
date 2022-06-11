package com.example.bibliosoph.model.repository

import com.example.bibliosoph.model.Book
import com.example.bibliosoph.model.room.relations.BookAndGenre
import com.example.bibliosoph.model.Genre
import kotlinx.coroutines.flow.Flow

interface BibliosophRepository {

    fun getBooks(): Flow<List<BookAndGenre>>

    suspend fun addBook(book: Book)

    suspend fun removeBook(book: Book)

    suspend fun getBookById(bookId: String): BookAndGenre

    suspend fun getGenres(): List<Genre>

    suspend fun addGenres(genres: List<Genre>)

    suspend fun getGenreById(genreId: String): Genre

    suspend fun getBooksByGenre(genreId: String): List<BookAndGenre>
}