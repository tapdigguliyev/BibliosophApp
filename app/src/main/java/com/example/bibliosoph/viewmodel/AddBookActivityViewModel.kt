package com.example.bibliosoph.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bibliosoph.model.Book
import com.example.bibliosoph.model.Genre
import com.example.bibliosoph.model.repository.BibliosophRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddBookActivityViewModel @Inject constructor(
    private val repository: BibliosophRepository
    ) : ViewModel() {

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

    var selectedSpinnerItemPosition = 0

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