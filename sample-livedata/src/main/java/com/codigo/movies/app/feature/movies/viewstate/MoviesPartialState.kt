package com.codigo.movies.app.feature.movies.viewstate

import com.codigo.movies.domain.model.Movie

sealed class MoviesPartialState {

    abstract fun reduce(oldState: MoviesViewState): MoviesViewState

    data class PopularResult(val movies: List<Movie>) : MoviesPartialState() {
        override fun reduce(oldState: MoviesViewState) = oldState.copy(popularMovies = movies)
    }

    object PopularLoaded : MoviesPartialState() {
        override fun reduce(oldState: MoviesViewState) = oldState.copy(
            loadingPopularMovies = false,
            loadPopularMoviesError = null
        )
    }

    object PopularLoading : MoviesPartialState() {
        override fun reduce(oldState: MoviesViewState) = oldState.copy(
            loadingPopularMovies = true,
            loadPopularMoviesError = null
        )
    }

    data class PopularError(val e: Throwable) : MoviesPartialState() {
        override fun reduce(oldState: MoviesViewState) = oldState.copy(
            loadingPopularMovies = false,
            loadPopularMoviesError = e
        )
    }

    data class UpcomingResult(val movies: List<Movie>) : MoviesPartialState() {
        override fun reduce(oldState: MoviesViewState) = oldState.copy(upcomingMovies = movies)
    }

    object UpcomingLoaded : MoviesPartialState() {
        override fun reduce(oldState: MoviesViewState) = oldState.copy(
            loadingUpcomingMovies = false,
            loadUpcomingMoviesError = null
        )
    }

    object UpcomingLoading : MoviesPartialState() {
        override fun reduce(oldState: MoviesViewState) = oldState.copy(
            loadingUpcomingMovies = true,
            loadUpcomingMoviesError = null
        )
    }

    data class UpcomingError(val e: Throwable) : MoviesPartialState() {
        override fun reduce(oldState: MoviesViewState) = oldState.copy(
            loadingUpcomingMovies = false,
            loadUpcomingMoviesError = e
        )
    }
}
