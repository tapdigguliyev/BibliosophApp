package com.example.bibliosoph.app

import android.app.Application
import com.example.bibliosoph.model.Genre
import com.example.bibliosoph.model.repository.BibliosophRepository
import com.example.bibliosoph.model.repository.BibliosophRepositoryImpl
import com.example.bibliosoph.model.room.BibliosophDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BibliosophApplication : Application() {

    companion object{
        lateinit var instance: BibliosophApplication

        private val database: BibliosophDatabase by lazy {
            BibliosophDatabase.buildDatabase(instance)
        }

        val repository: BibliosophRepository by lazy {
            BibliosophRepositoryImpl(
                database.bookDao(),
                database.genreDao()
            )
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        GlobalScope.launch {
            if (repository.getGenres().isEmpty()) {
                repository.addGenres(
                    listOf(
                        Genre(name = "Action"),
                        Genre(name = "Adventure"),
                        Genre(name = "Classic"),
                        Genre(name = "Philosophy"),
                        Genre(name = "Mystery"),
                        Genre(name = "Fantasy"),
                        Genre(name = "Sci-Fi"),
                        Genre(name = "History"),
                        Genre(name = "Horror"),
                        Genre(name = "Romance"),
                        Genre(name = "Short Story"),
                        Genre(name = "Biography"),
                        Genre(name = "Poetry"),
                        Genre(name = "Self-Help"),
                        Genre(name = "Young novel"),
                        Genre(name = "Dystopian")
                    )
                )
            }
        }
    }
}