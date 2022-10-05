package com.example.bibliosoph.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BibliosophApplication : Application() {

    companion object{
        lateinit var instance: BibliosophApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}