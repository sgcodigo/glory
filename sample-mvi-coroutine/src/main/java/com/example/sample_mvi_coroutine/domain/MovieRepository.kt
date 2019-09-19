package com.example.sample_mvi_coroutine.domain

import androidx.lifecycle.LiveData
import com.example.sample_mvi_coroutine.domain.model.Movie

interface MovieRepository {

    suspend fun fetchPopularMovies(): List<Movie>

    suspend fun fetchUpcomingMovies(): List<Movie>

    fun streamPopularMovies(): LiveData<List<Movie>>

    fun streamUpcomingMovies(): LiveData<List<Movie>>
}