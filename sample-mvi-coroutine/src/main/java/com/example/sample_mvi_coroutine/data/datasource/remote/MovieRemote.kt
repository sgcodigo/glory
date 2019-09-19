package com.example.sample_mvi_coroutine.data.datasource.remote

import com.example.sample_mvi_coroutine.domain.Result
import com.example.sample_mvi_coroutine.domain.model.Movie

interface MovieRemote {

    suspend fun fetchPopularMovies(): List<Movie>

    suspend fun fetchUpcomingMovies(): List<Movie>
}