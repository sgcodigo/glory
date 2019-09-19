package com.example.sample_mvi_coroutine.data.datasource.remote

import com.example.sample_mvi_coroutine.BuildConfig
import com.example.sample_mvi_coroutine.data.network.service.MovieService
import com.example.sample_mvi_coroutine.domain.model.Movie

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