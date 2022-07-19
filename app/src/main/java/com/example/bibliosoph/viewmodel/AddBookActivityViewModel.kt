package com.example.bibliosoph.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bibliosoph.app.BibliosophApplication
import com.example.bibliosoph.model.Book
import com.example.bibliosoph.model.Genre
import com.example.bibliosoph.model.repository.BibliosophRepository
import com.example.bibliosoph.model.repository.BibliosophRepositoryImpl
import kotlinx.coroutines.launch
import java.util.*

class AddBookActivityViewModel(
    private val repository: BibliosophRepository =
        BibliosophRepositoryImpl(
            BibliosophApplication.database.bookDao(),
            BibliosophApplication.database.genreDao()
        )) : ViewModel() {

    init {
        viewModelScope.launch {
            if (repository.getGenres().isEmpty()) {
                repository.addGenres(
                    listOf(
                        Genre(name = "Action"),
                        Genre(name = "Adventure"),
                        Genre(name = "Classic"),
                        Genre(name = "Philosophy"),
                        Genre(name = "Mystery"),
                        Genre(name = "Fantasy"),
                        Genre(name = "Sci-Fi"),
                        Genre(name = "History"),
                        Genre(name = "Horror"),
                        Genre(name = "Romance"),
                        Genre(name = "Short Story"),
                        Genre(name = "Biography"),
                        Genre(name = "Poetry"),
                        Genre(name = "Self-Help"),
                        Genre(name = "Young novel"),
                        Genre(name = "Dystopian")
                    )
                )
            }
        }
    }

    var bookId: String = ""
    var bookName: String = ""
    var startDate: Date? = null
    var endDate: Date? = null
    var pageCount: String? = ""
    var writerName: String? = ""
    var genreId: String? = ""

    suspend fun addBook() {
        if (bookId.isNotBlank() && bookName.isNotBlank() && !genreId.isNullOrBlank()) {
            repository.addBook(
                Book(
                    bookId,
                    bookName,
                    startDate,
                    endDate,
                    pageCount,
                    writerName,
                    genreId
                )
            )
        }
    }

    suspend fun getGenres() = repository.getGenres()
}