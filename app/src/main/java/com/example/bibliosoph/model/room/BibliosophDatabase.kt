package com.example.bibliosoph.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.bibliosoph.model.Book
import com.example.bibliosoph.model.Genre
import com.example.bibliosoph.model.room.daos.BookDao
import com.example.bibliosoph.model.room.daos.GenreDao
import com.example.bibliosoph.model.room.migrations.migration_1_2
import com.example.bibliosoph.model.room.typeconverters.DateConverter


const val DATABASE_VERSION = 2

@Database(
    entities = [Book::class, Genre::class],
    version = DATABASE_VERSION,
    exportSchema = false
)

//@TypeConverters(DateConverter::class)
abstract class BibliosophDatabase : RoomDatabase() {

    companion object {
        private const val DATABASE_NAME = "Bibliosoph"

        fun buildDatabase(context: Context): BibliosophDatabase {
            return Room.databaseBuilder(
                context,
                BibliosophDatabase::class.java,
                DATABASE_NAME
            )
                .addMigrations(migration_1_2)
                .build()
        }
    }

    abstract fun bookDao(): BookDao
    abstract fun genreDao(): GenreDao
}