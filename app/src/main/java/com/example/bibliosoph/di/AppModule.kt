package com.example.bibliosoph.di

import android.content.Context
import androidx.room.Room
import com.example.bibliosoph.model.repository.BibliosophRepository
import com.example.bibliosoph.model.repository.BibliosophRepositoryImpl
import com.example.bibliosoph.model.room.BibliosophDatabase
import com.example.bibliosoph.model.room.migrations.migration_1_2
import com.example.bibliosoph.other.Constants.Companion.BIBLIOSOPH_DATABASE_NAME
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBibliosophDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        BibliosophDatabase::class.java,
        BIBLIOSOPH_DATABASE_NAME)
        .addMigrations(migration_1_2)
        .build()

    @Provides
    @Singleton
    fun provideBookDao(db: BibliosophDatabase) = db.bookDao()

    @Provides
    @Singleton
    fun provideGenreDao(db: BibliosophDatabase) = db.genreDao()

    @Provides
    @Singleton
    fun provideBibliosophRepository(bibliosophRepositoryImpl: BibliosophRepositoryImpl): BibliosophRepository {
        return bibliosophRepositoryImpl
    }
}