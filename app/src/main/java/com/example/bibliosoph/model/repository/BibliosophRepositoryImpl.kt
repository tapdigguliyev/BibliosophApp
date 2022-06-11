package com.example.bibliosoph.model.repository

import com.example.bibliosoph.model.Book
import com.example.bibliosoph.model.room.relations.BookAndGenre
import com.example.bibliosoph.model.Genre
import com.example.bibliosoph.model.room.BookDao
import com.example.bibliosoph.model.room.GenreDao

class BibliosophRepositoryImpl(
    private val bookDao: BookDao,
    private val genreDao: GenreDao
) : BibliosophRepository {

    override fun getBooks(): List<BookAndGenre> = bookDao.getBooks()

    override fun addBook(book: Book) = bookDao.addBook(book)

    override fun removeBook(book: Book) = bookDao.removeBook(book)

    override fun getBookById(bookId: String): BookAndGenre = bookDao.getBookById(bookId)

    override fun getGenres(): List<Genre> = genreDao.getGenres()

    override fun addGenres(genres: List<Genre>) = genreDao.addGenres(genres)

    override fun getGenreById(genreId: String): Genre = genreDao.getGenreById(genreId)

    override fun getBooksByGenre(genreId: String): List<BookAndGenre> =
        genreDao.getBooksByGenre(genreId).let { booksByGenre ->
            val books = booksByGenre.books ?: return emptyList()
            return books.map { BookAndGenre(it, booksByGenre.genre) }
        }
}