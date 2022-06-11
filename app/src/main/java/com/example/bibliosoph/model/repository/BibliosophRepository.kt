package com.example.bibliosoph.model.repository

import com.example.bibliosoph.model.Book
import com.example.bibliosoph.model.room.relations.BookAndGenre
import com.example.bibliosoph.model.Genre

interface BibliosophRepository {

    fun getBooks(): List<BookAndGenre>

    fun addBook(book: Book)

    fun removeBook(book: Book)

    fun getBookById(bookId: String): BookAndGenre

    fun getGenres(): List<Genre>

    fun addGenres(genres: List<Genre>)

    fun getGenreById(genreId: String): Genre
}