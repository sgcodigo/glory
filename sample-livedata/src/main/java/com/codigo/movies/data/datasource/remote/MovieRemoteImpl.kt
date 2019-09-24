package com.codigo.movies.data.datasource.remote

import com.codigo.movies.BuildConfig
import com.codigo.movies.data.network.service.MovieService
import com.codigo.movies.data.toDomain

class MovieRemoteImpl(
    private val movieService: MovieService
) : MovieRemote {

    override suspend fun fetchUpcomingMovies() = movieService
        .getUpcomingMovies(BuildConfig.ApiKey).results.toDomain()

    override suspend fun fetchPopularMovies() = movieService
        .getPopularMovies(BuildConfig.ApiKey).results.toDomain()
}
