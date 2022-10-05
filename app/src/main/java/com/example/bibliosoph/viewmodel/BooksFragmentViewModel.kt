package com.example.bibliosoph.viewmodel

import androidx.lifecycle.ViewModel
import com.example.bibliosoph.model.Book
import com.example.bibliosoph.model.repository.BibliosophRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BooksFragmentViewModel @Inject constructor(
    private val repository: BibliosophRepository
    ) : ViewModel() {

    suspend fun getBooks() = repository.getBooks().reversed()
    suspend fun removeBook(book: Book) = repository.removeBook(book)
}