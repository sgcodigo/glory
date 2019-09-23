package com.codigo.movies.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [Index(value = ["movieId", "type"], unique = true)],
    tableName = "movies")
data class MovieEntity(

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    var movieId: Long,

    val title: String,

    var type: String,

    @ColumnInfo(name = "poster_path")
    val posterPath: String

) {
    companion object {
        const val TYPE_POPULAR = "popular"
        const val TYPE_UPCOMING = "upcoming"
    }
}