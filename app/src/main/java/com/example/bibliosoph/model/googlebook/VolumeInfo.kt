package com.example.bibliosoph.model.googlebook

data class VolumeInfo(
    val authors: List<String>? = null,
    val imageLinks: ImageLinks? = null,
    val language: String? = null,
    val pageCount: Int? = null,
    val title: String? = null
)