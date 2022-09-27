package com.example.bibliosoph.model.api

import com.example.bibliosoph.model.googlebook.GoogleBooksResponse
import com.example.bibliosoph.other.ApiKey.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BooksApi {

    @GET("/books/v1/volumes")
    suspend fun getSearchBooks(
        @Query("q")
        searchQuery: String,
        @Query("key")
        apiKey: String = API_KEY
    ) : Response<GoogleBooksResponse>
}