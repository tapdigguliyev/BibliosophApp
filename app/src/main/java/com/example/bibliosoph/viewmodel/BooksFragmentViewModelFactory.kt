package com.example.bibliosoph.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bibliosoph.model.repository.BibliosophRepository

class BooksFragmentViewModelFactory(private val repository: BibliosophRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BooksFragmentViewModel(repository) as T
    }
}