package com.codigo.movies.data.model.data

import com.squareup.moshi.Json

data class MovieData(

    val id: Long,

    val title: String,

    @field:Json(name = "poster_path")
    val posterPath: String
)