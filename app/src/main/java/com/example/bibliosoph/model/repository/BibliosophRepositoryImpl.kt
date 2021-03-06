package com.example.bibliosoph.model.repository

import com.example.bibliosoph.model.Book
import com.example.bibliosoph.model.room.relations.BookAndGenre
import com.example.bibliosoph.model.Genre
import com.example.bibliosoph.model.room.daos.BookDao
import com.example.bibliosoph.model.room.daos.GenreDao
import kotlinx.coroutines.flow.Flow

class BibliosophRepositoryImpl(
    private val bookDao: BookDao,
    private val genreDao: GenreDao
) : BibliosophRepository {

    override suspend fun getBooks(): List<BookAndGenre> = getBooksIds().map { getBookById(it.toString()) }

    override suspend fun getBooksIds(): List<Int> =
        bookDao.getBooksIds().map { it.toInt() }.toSortedSet().toList()

    override suspend fun addBook(book: Book) = bookDao.addBook(book)

    override suspend fun removeBook(book: Book) = bookDao.removeBook(book)

    override suspend fun getBookById(bookId: String): BookAndGenre = bookDao.getBookById(bookId)

    override suspend fun getGenres(): List<Genre> = genreDao.getGenres()

    override suspend fun addGenres(genres: List<Genre>) = genreDao.addGenres(genres)

    override suspend fun getGenreById(genreId: String): Genre = genreDao.getGenreById(genreId)

    override suspend fun getBooksByGenre(genreId: String): List<BookAndGenre> =
        genreDao.getBooksByGenre(genreId).let { booksByGenre ->
            val books = booksByGenre.books ?: return emptyList()
            return books.map { BookAndGenre(it, booksByGenre.genre) }
        }
}