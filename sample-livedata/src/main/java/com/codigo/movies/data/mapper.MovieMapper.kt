package com.codigo.movies.data

import com.codigo.movies.data.model.data.MovieData
import com.codigo.movies.data.model.entity.MovieEntity
import com.codigo.movies.domain.model.Movie

@JvmName("MovieDataToDomain")
fun List<MovieData>.toDomain() = map {
    Movie(it.id, it.title, it.posterPath)
}

@JvmName("MovieEntityToDomain")
fun List<MovieEntity>.toDomain() = map {
    Movie(it.dbId, it.title, it.posterPath)
}

@JvmName("MovieDomainToEntity")
fun List<Movie>.toEntity(type: String) = map {
    MovieEntity(
        dbId = it.id + type,
        type = type,
        title = it.title,
        posterPath = it.posterPath
    )
}