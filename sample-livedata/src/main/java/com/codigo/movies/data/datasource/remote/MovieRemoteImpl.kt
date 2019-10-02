package com.codigo.movies.data.datasource.remote

import com.codigo.movies.BuildConfig
import com.codigo.movies.data.network.service.MovieService
import com.codigo.movies.data.toDomain
import com.codigo.movies.domain.Either

class MovieRemoteImpl(
    private val movieService: MovieService
) : MovieRemote {

    override suspend fun fetchUpcomingMovies() = try {
        movieService.getUpcomingMovies(BuildConfig.ApiKey).results.toDomain().let {
            Either.Right(it)
        }
    } catch (e: Exception) {
        Either.Left(e)
    }

    override suspend fun fetchPopularMovies() = try {
        movieService.getPopularMovies(BuildConfig.ApiKey).results.toDomain().let {
            Either.Right(it)
        }
    } catch (e: Exception) {
        Either.Left(e)
    }
}
