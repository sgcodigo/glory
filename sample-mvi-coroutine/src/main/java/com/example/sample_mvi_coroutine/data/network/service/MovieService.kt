package com.example.sample_mvi_coroutine.data.network.service

import com.example.sample_mvi_coroutine.domain.model.Movie
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("movie/now_playing")
    suspend fun getUpcomingMovies(
        @Query("api_key") apiKey: String
    ): MovieResponse

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String
    ): MovieResponse
}

data class MovieResponse(
    val results: List<Movie> = emptyList()
)