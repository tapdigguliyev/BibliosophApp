package com.example.bibliosoph.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.*
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bibliosoph.app.BibliosophApplication
import com.example.bibliosoph.model.googlebook.GoogleBooksResponse
import com.example.bibliosoph.model.repository.GoogleBooksRepository
import com.example.bibliosoph.other.Constants.Companion.QUERY_PAGE_SIZE
import com.example.bibliosoph.other.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class GoogleBooksViewModel(app: Application, private val booksRepository: GoogleBooksRepository) : AndroidViewModel(app) {
    val searchBooks: MutableLiveData<Resource<GoogleBooksResponse>> = MutableLiveData()
    var searchBooksResponse: GoogleBooksResponse? = null
//    var searchBooksPage = 1
    var startIndex = 0

    companion object {
        const val TAG = "ViewModel"
    }

    fun searchForBooks(searchQuery: String) = viewModelScope.launch {
        safeSearchBooks(searchQuery)
    }

    private fun handleSearchBooksResponse(response: Response<GoogleBooksResponse>) : Resource<GoogleBooksResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
//                searchBooksPage++
                Log.d(TAG, "startIndexBefore: $startIndex")
                startIndex += QUERY_PAGE_SIZE
                Log.d(TAG, "startIndexAfter: $startIndex")
                if (searchBooksResponse == null) {
                    searchBooksResponse = resultResponse
                } else {
                    //In the line below, we write "oldItems = searchBooksResponse?.items",
                    // we don't create a copy of the books response items,
                    // instead in Java/Kotlin it will create a reference.
                    // That means, whatever we change in oldItems is actually a change in searchBooksResponse.items,
                    // since oldItems only refers to it, it is not an independent copy.
                    val oldItems = searchBooksResponse?.items
                    val newItems = resultResponse.items
                    oldItems?.addAll(newItems)
                }
                return Resource.Success(searchBooksResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private suspend fun safeSearchBooks(searchQuery: String) {
        searchBooks.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = booksRepository.getSearchBooks(searchQuery, QUERY_PAGE_SIZE.toString(), startIndex.toString())
                searchBooks.postValue(handleSearchBooksResponse(response))
            } else {
                searchBooks.postValue(Resource.Error("No internet connection"))
            }

        } catch (error: Throwable) {
            when(error) {
                is IOException -> searchBooks.postValue(Resource.Error("Network Failure"))
                else -> searchBooks.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<BibliosophApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when {
            capabilities.hasTransport(TRANSPORT_WIFI) -> true
            capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}