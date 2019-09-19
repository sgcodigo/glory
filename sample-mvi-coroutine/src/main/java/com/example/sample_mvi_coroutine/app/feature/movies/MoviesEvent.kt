package com.example.sample_mvi_coroutine.app.feature.movies

sealed class MoviesEvent {
    data class LoadUpcomingMovieError(val e: Exception): MoviesEvent()
    data class LoadPopularMovieError(val e: Exception): MoviesEvent()
}