package com.example.bibliosoph.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bibliosoph.model.googlebook.GoogleBooksResponse
import com.example.bibliosoph.model.repository.GoogleBooksRepository
import com.example.bibliosoph.other.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class GoogleBooksViewModel(private val booksRepository: GoogleBooksRepository) : ViewModel() {
    val searchBooks: MutableLiveData<Resource<GoogleBooksResponse>> = MutableLiveData()

    fun searchForBooks(searchQuery: String) = viewModelScope.launch {
        searchBooks.postValue(Resource.Loading())
        val response = booksRepository.getSearchBooks(searchQuery)
        searchBooks.postValue(handleSearchBooksResponse(response))
    }

    private fun handleSearchBooksResponse(response: Response<GoogleBooksResponse>) : Resource<GoogleBooksResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}