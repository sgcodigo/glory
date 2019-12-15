package com.codigo.movies.data.datasource.remote

import com.codigo.movies.BuildConfig
import com.codigo.movies.data.mapper.toDomain
import com.codigo.movies.data.network.service.MovieService
import com.codigo.movies.data.util.handleCall
import retrofit2.Retrofit

class MovieRemoteImpl(
    private val movieService: MovieService,
    private val retrofit: Retrofit
) : MovieRemote {

    override suspend fun fetchUpcomingMovies() =
        retrofit.handleCall(
            fallbackErrorMessage = "Failed to fetch upcoming movies",
            apiCall = { movieService.getUpcomingMovies(BuildConfig.ApiKey) },
            mapper = { it.results.toDomain() }
        )

    override suspend fun fetchPopularMovies() =
        retrofit.handleCall(
            fallbackErrorMessage = "Failed to fetch popular movies",
            apiCall = { movieService.getPopularMovies(BuildConfig.ApiKey) },
            mapper = { it.results.toDomain() }
        )
}
