package com.example.bibliosoph.model.repository

import com.example.bibliosoph.model.api.RetrofitInstance

class GoogleBooksRepository {

    suspend fun getSearchBooks(searchQuery: String) =
        RetrofitInstance.api.getSearchBooks(searchQuery, "40")
}