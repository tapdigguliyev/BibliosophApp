package com.example.bibliosoph.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bibliosoph.model.api.NetworkStatusChecker
import com.example.bibliosoph.model.repository.GoogleBooksRepository

class GoogleBooksViewModelProviderFactory(
    private val booksRepository: GoogleBooksRepository,
    private val networkStatusChecker: NetworkStatusChecker
    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GoogleBooksViewModel(booksRepository, networkStatusChecker) as T
    }
}