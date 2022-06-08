package com.example.bibliosoph.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "books")
data class Book(
    @PrimaryKey
    val id: Int,
    val name: String,
    var startDate: Date? = null,
    var endDate: Date? = null,
    var pageNumber: Int? = null,
    @ColumnInfo(name = "bookGenreId")
    var genreId: String? = null,
    var isCompleted: Boolean? = false
) : Parcelable