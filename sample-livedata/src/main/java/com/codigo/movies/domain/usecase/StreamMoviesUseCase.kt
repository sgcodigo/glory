package com.codigo.movies.domain.usecase

import com.codigo.movies.data.model.entity.MovieEntity
import com.codigo.movies.domain.repository.MovieRepository

class StreamMoviesUseCase(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(type: String) = when (type) {
        MovieEntity.TYPE_POPULAR -> movieRepository.streamPopularMovies()
        MovieEntity.TYPE_UPCOMING -> movieRepository.streamUpcomingMovies()
        else -> throw IllegalArgumentException("Unknown type: $type")
    }
}
