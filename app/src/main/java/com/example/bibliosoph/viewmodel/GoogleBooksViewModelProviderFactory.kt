package com.example.bibliosoph.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bibliosoph.model.repository.GoogleBooksRepository

class GoogleBooksViewModelProviderFactory(private val booksRepository: GoogleBooksRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GoogleBooksViewModel(booksRepository) as T
    }
}