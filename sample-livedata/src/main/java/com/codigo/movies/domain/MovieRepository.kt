package com.codigo.movies.domain

import androidx.lifecycle.LiveData
import com.codigo.movies.domain.model.Movie

interface MovieRepository {

    suspend fun fetchPopularMovies(): List<Movie>

    suspend fun fetchUpcomingMovies(): List<Movie>

    fun streamPopularMovies(): LiveData<List<Movie>>

    fun streamUpcomingMovies(): LiveData<List<Movie>>
}