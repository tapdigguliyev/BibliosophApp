package com.example.bibliosoph.app

import android.app.Application
import com.example.bibliosoph.model.room.BibliosophDatabase

class BibliosophApplication : Application() {

    companion object{
        lateinit var instance: BibliosophApplication

        val database: BibliosophDatabase by lazy {
            BibliosophDatabase.buildDatabase(instance)
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}