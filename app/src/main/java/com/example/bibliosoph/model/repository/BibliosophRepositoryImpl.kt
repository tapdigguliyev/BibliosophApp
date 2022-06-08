package com.example.bibliosoph.model.repository

import com.example.bibliosoph.model.room.BookDao
import com.example.bibliosoph.model.room.GenreDao

class BibliosophRepositoryImpl(
    private val bookDao: BookDao,
    private val genreDao: GenreDao
) : BibliosophRepository {

}