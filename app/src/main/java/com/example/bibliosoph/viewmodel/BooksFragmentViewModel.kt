package com.example.bibliosoph.viewmodel

import androidx.lifecycle.ViewModel
import com.example.bibliosoph.app.BibliosophApplication
import com.example.bibliosoph.model.Book
import com.example.bibliosoph.model.repository.BibliosophRepository
import com.example.bibliosoph.model.repository.BibliosophRepositoryImpl

class BooksFragmentViewModel(
    private val repository: BibliosophRepository =
        BibliosophRepositoryImpl(
            BibliosophApplication.database.bookDao(),
            BibliosophApplication.database.genreDao()
        )) : ViewModel() {

    suspend fun getBooks() = repository.getBooks().reversed()
    suspend fun removeBook(book: Book) = repository.removeBook(book)
}