package com.codigo.movies.data.model.response

import com.codigo.movies.data.model.data.MovieData

data class MovieResponse(
    val results: List<MovieData> = emptyList()
)
