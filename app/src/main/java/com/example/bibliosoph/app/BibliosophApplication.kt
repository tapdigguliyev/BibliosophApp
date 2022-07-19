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

        val database: BibliosophDatabase by lazy {
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
    }
}