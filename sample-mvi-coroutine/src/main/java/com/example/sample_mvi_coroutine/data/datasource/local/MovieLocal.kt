package com.example.sample_mvi_coroutine.data.datasource.local

import androidx.lifecycle.LiveData
import com.example.sample_mvi_coroutine.domain.model.Movie

interface MovieLocal {

    fun streamPopularMovies(): LiveData<List<Movie>>
    fun insertPopularMovies(movies: List<Movie>)

    fun streamUpcomingMovies(): LiveData<List<Movie>>
    fun insertUpcomingMovies(movies: List<Movie>)
}