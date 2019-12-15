package com.codigo.movies.domain.repository

import com.codigo.movies.domain.model.Movie
import com.codigo.movies.domain.type.Either
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun fetchPopularMovies(): Either<Throwable, List<Movie>>

    suspend fun fetchUpcomingMovies(): Either<Throwable, List<Movie>>

    fun streamPopularMovies(): Flow<List<Movie>>

    fun streamUpcomingMovies(): Flow<List<Movie>>
}
