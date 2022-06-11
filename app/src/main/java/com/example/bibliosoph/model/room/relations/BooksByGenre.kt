package com.example.bibliosoph.model.room.relations

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import com.example.bibliosoph.model.Book
import com.example.bibliosoph.model.Genre
import kotlinx.parcelize.Parcelize

@Parcelize
data class BooksByGenre(
    @Embedded
    val genre: Genre,
    @Relation(parentColumn = "id", entityColumn = "bookGenreId")
    val books: List<Book>?
) : Parcelable