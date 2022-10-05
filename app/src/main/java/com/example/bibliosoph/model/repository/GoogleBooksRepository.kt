package com.example.bibliosoph.model.repository

import com.example.bibliosoph.model.api.BooksApi
import com.example.bibliosoph.model.googlebook.GoogleBooksResponse
import retrofit2.Response

class GoogleBooksRepository(private val booksApi: BooksApi) {

    suspend fun getSearchBooks(searchQuery: String, maxResults: String, startIndex: String): Response<GoogleBooksResponse> {
        return booksApi.getSearchBooks("intitle:$searchQuery", maxResults, startIndex)
    }
}