package com.codigo.movies.data.datasource.remote

import com.codigo.movies.domain.Either
import com.codigo.movies.domain.model.Movie

interface MovieRemote {

    suspend fun fetchPopularMovies(): Either<Throwable, List<Movie>>

    suspend fun fetchUpcomingMovies(): Either<Throwable, List<Movie>>
}
