package com.codigo.movies.data.datasource.remote

import com.codigo.movies.BuildConfig
import com.codigo.movies.data.network.service.MovieService
import com.codigo.movies.domain.model.Movie

class MovieRemoteImpl(
    private val movieService: MovieService
): MovieRemote {

    override suspend fun fetchUpcomingMovies(): List<Movie> {
        return movieService.getUpcomingMovies(BuildConfig.ApiKey).results
    }

    override suspend fun fetchPopularMovies(): List<Movie> {
        return movieService.getPopularMovies(BuildConfig.ApiKey).results
    }
}