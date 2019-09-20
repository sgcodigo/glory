package com.codigo.movies.app.feature.movies

import androidx.lifecycle.*
import com.codigo.mvi.livedata.MviViewModel
import com.codigo.movies.domain.MovieRepository
import com.codigo.movies.domain.viewstate.movie.MoviesViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val movieRepository: MovieRepository
) : MviViewModel<MoviesViewState, MoviesEvent>() {

    private var movieState = MoviesViewState()

    init {
        // observe movies
        viewStateLiveData.addSource(movieRepository.streamPopularMovies().map {
            movieState.copy(popularMovies = it)
        }) { cacheAndEmitState(it) }

        viewStateLiveData.addSource(movieRepository.streamUpcomingMovies().map {
            movieState.copy(upcomingMovies = it)
        }) { cacheAndEmitState(it) }

        // first time refresh from server
        fetchPopularMovies()
        fetchUpcomingMovies()
    }

    fun fetchPopularMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            // emit loading status
            movieState.copy(loadingPopularMovies = true, loadPopularMoviesError = null)
                .also { cacheAndEmitState(it) }
            // load movies
            try {
                movieRepository.fetchPopularMovies()
                movieState.copy(loadingPopularMovies = false)
                    .also { cacheAndEmitState(it) }
            } catch (e: Exception) {
                movieState.copy(loadPopularMoviesError = e, loadingPopularMovies = false)
                    .also { cacheAndEmitState(it) }
            }
        }
    }

    fun fetchUpcomingMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            // emit loading status
            movieState.copy(loadingUpcomingMovies = true, loadUpcomingMoviesError = null)
                .also { cacheAndEmitState(it) }
            // load movies
            try {
                movieRepository.fetchUpcomingMovies()
                movieState.copy(loadingUpcomingMovies = false)
                    .also { cacheAndEmitState(it) }
            } catch (e: Exception) {
                movieState.copy(loadUpcomingMoviesError = e, loadingUpcomingMovies = false)
                    .also { cacheAndEmitState(it) }
            }
        }
    }

    private fun cacheAndEmitState(state: MoviesViewState) {
        movieState = state
        viewStateLiveData.postValue(state)
    }
}