package com.codigo.movies.domain.usecase

import com.codigo.movies.data.model.entity.MovieEntity
import com.codigo.movies.domain.repository.MovieRepository

class GetMoviesUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(type: String) = when (type) {
        MovieEntity.TYPE_POPULAR -> movieRepository.fetchPopularMovies()
        MovieEntity.TYPE_UPCOMING -> movieRepository.fetchUpcomingMovies()
        else -> throw IllegalArgumentException("Unknown type: $type")
    }

}
