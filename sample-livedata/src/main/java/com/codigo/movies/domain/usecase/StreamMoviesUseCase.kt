package com.codigo.movies.domain.usecase

import androidx.lifecycle.LiveData
import com.codigo.movies.data.model.entity.MovieEntity
import com.codigo.movies.domain.Either
import com.codigo.movies.domain.model.Movie
import com.codigo.movies.domain.repository.MovieRepository

class StreamMoviesUseCase(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(type: String): LiveData<List<Movie>> {
        return when (type) {
            MovieEntity.TYPE_POPULAR -> movieRepository.streamPopularMovies()
            MovieEntity.TYPE_UPCOMING -> movieRepository.streamUpcomingMovies()
            else -> throw IllegalArgumentException("Unknown type: $type")
        }
    }
}