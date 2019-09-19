package com.codigo.movies.domain.model

import com.squareup.moshi.Json

data class Movie(
    val id: String,
    val title: String,
    @field:Json(name = "poster_path") val posterPath: String
)