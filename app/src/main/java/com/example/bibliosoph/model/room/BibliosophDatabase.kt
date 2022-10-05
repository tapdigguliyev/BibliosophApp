package com.example.bibliosoph.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.bibliosoph.model.Book
import com.example.bibliosoph.model.Genre
import com.example.bibliosoph.model.room.daos.BookDao
import com.example.bibliosoph.model.room.daos.GenreDao
import com.example.bibliosoph.model.room.typeconverters.DateConverter
import com.example.bibliosoph.other.Constants.Companion.DATABASE_VERSION

@Database(
    entities = [Book::class, Genre::class],
    version = DATABASE_VERSION,
    exportSchema = false
)

@TypeConverters(DateConverter::class)
abstract class BibliosophDatabase : RoomDatabase() {

    abstract fun bookDao(): BookDao
    abstract fun genreDao(): GenreDao
}