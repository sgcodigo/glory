package com.codigo.movies.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(
//    indices = [Index(value = ["id", "type"], unique = true)],
    tableName = "movies")
data class Movie(

    @PrimaryKey
    var dbId: String,

    val id: String,

    val title: String,

    var type: String = "",

    @ColumnInfo(name = "poster_path")
    @field:Json(name = "poster_path")
    val posterPath: String
) {
    companion object {
        const val TYPE_POPULAR = "popular"
        const val TYPE_UPCOMING = "upcoming"
    }
}