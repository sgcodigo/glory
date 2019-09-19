package com.example.sample_mvi_coroutine.domain.viewstate.movie

import com.example.sample_mvi_coroutine.domain.model.Movie

data class MoviesViewState(
    val upcomingMovies: List<Movie> = emptyList(),
    val loadingUpcomingMovies: Boolean = false,
    val loadUpcomingMoviesError: Throwable? = null,

    val popularMovies: List<Movie> = emptyList(),
    val loadingPopularMovies: Boolean = false,
    val loadPopularMoviesError: Throwable? = null
)