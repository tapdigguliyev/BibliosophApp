package com.example.bibliosoph.model

import android.os.Parcelable
import androidx.room.*
import com.example.bibliosoph.model.room.typeconverters.DateConverter
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "books")
data class Book(
    @PrimaryKey
    val id: String,
    val name: String,
    @TypeConverters(DateConverter::class)
    var startDate: Date? = null,
    @TypeConverters(DateConverter::class)
    var endDate: Date? = null,
    var pageNumber: String? = null,
    @ColumnInfo(name = "bookGenreId")
    var genreId: String? = null,
    var isCompleted: Boolean? = false
) : Parcelable