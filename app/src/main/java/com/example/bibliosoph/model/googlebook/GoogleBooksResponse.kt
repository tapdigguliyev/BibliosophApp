package com.example.bibliosoph.model.googlebook

data class GoogleBooksResponse(
    val items: MutableList<Item>,
    val kind: String,
    val totalItems: Int
)