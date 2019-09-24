package com.codigo.movies.domain.viewstate.movie

import com.codigo.movies.domain.model.Movie

data class MoviesViewState(
    val upcomingMovies: List<Movie> = emptyList(),
    val loadingUpcomingMovies: Boolean = false,
    val loadUpcomingMoviesError: Throwable? = null,

    val popularMovies: List<Movie> = emptyList(),
    val loadingPopularMovies: Boolean = false,
    val loadPopularMoviesError: Throwable? = null
)
