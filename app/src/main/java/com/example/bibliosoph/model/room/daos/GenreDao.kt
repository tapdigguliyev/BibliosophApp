package com.example.bibliosoph.model.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bibliosoph.model.Genre
import com.example.bibliosoph.model.room.relations.BooksByGenre

@Dao
interface GenreDao {

    @Query("SELECT * FROM genres")
    suspend fun getGenres(): List<Genre>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGenres(genres: List<Genre>)

    @Query("SELECT * FROM genres WHERE id = :genreId")
    suspend fun getGenreById(genreId: String): Genre

    @Query("SELECT * FROM genres WHERE id = :genreId")
    suspend fun getBooksByGenre(genreId: String): BooksByGenre
}