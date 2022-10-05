package com.example.bibliosoph.model.repository

import com.example.bibliosoph.model.api.RetrofitInstance
import com.example.bibliosoph.model.googlebook.GoogleBooksResponse
import retrofit2.Response

class GoogleBooksRepository {

    suspend fun getSearchBooks(searchQuery: String, maxResults: String, startIndex: String): Response<GoogleBooksResponse> {
        return RetrofitInstance.api.getSearchBooks("intitle:$searchQuery", maxResults, startIndex)
    }
}