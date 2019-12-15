package com.codigo.movies.data.datasource.local

import com.codigo.movies.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieLocal {

    fun streamPopularMovies(): Flow<List<Movie>>
    suspend fun insertPopularMovies(movies: List<Movie>)

    fun streamUpcomingMovies(): Flow<List<Movie>>
    suspend fun insertUpcomingMovies(movies: List<Movie>)
}
