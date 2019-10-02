package com.codigo.movies.domain.repository

import androidx.lifecycle.LiveData
import com.codigo.movies.domain.Either
import com.codigo.movies.domain.model.Movie

interface MovieRepository {

    suspend fun fetchPopularMovies(): Either<Throwable, List<Movie>>

    suspend fun fetchUpcomingMovies(): Either<Throwable, List<Movie>>

    fun streamPopularMovies(): LiveData<List<Movie>>

    fun streamUpcomingMovies(): LiveData<List<Movie>>
}
