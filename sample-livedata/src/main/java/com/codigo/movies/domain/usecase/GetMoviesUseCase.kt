package com.codigo.movies.domain.usecase

import com.codigo.movies.data.model.entity.MovieEntity
import com.codigo.movies.domain.Either
import com.codigo.movies.domain.model.Movie
import com.codigo.movies.domain.repository.MovieRepository

class GetMoviesUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(type: String): Either<Throwable, List<Movie>> {
        return try {
            val result = when (type) {
                MovieEntity.TYPE_POPULAR -> movieRepository.fetchPopularMovies()
                MovieEntity.TYPE_UPCOMING -> movieRepository.fetchUpcomingMovies()
                else -> throw IllegalArgumentException("Unknown type: $type")
            }
            Either.Right(result)
        } catch (e: Exception) {
            Either.Left(e)
        }
    }
}
