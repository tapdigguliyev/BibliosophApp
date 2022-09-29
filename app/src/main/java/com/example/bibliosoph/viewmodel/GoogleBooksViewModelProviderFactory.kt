package com.example.bibliosoph.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bibliosoph.model.repository.GoogleBooksRepository

class GoogleBooksViewModelProviderFactory(
    private val app: Application,
    private val booksRepository: GoogleBooksRepository
    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GoogleBooksViewModel(app, booksRepository) as T
    }
}