package com.codigo.movies.app.feature.movies

import androidx.lifecycle.*
import com.codigo.mvi.livedata.MviViewModel
import com.codigo.movies.domain.MovieRepository
import com.codigo.movies.domain.viewstate.movie.MoviesViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val movieRepository: MovieRepository
) : MviViewModel<MoviesViewState, MoviesEvent>() {

    private var movieState = MoviesViewState()

    init {
        // observe movies
        viewStateLiveData.addSource(movieRepository.streamPopularMovies().map {
            movieState.copy(popularMovies = it, loadingPopularMovies = false, loadPopularMoviesError = null).also {
                movieState = it
            }
        }) { viewStateLiveData.value = it }

        viewStateLiveData.addSource(movieRepository.streamUpcomingMovies().map {
            movieState.copy(upcomingMovies = it, loadingUpcomingMovies = false, loadUpcomingMoviesError = null).also {
                movieState = it
            }
        }) { viewStateLiveData.value = it }

        // first time refresh from server
        fetchPopularMovies()
        fetchUpcomingMovies()
    }

    fun fetchUpcomingMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            // emit loading status
            movieState.copy(loadingUpcomingMovies = true, loadUpcomingMoviesError = null).also {
                movieState = it
                viewStateLiveData.postValue(it)
            }
            // load movies
            try {
                movieRepository.fetchUpcomingMovies()
            } catch (e: Exception) {
                movieState.copy(loadUpcomingMoviesError = e, loadingUpcomingMovies = false).also {
                    movieState = it
                    viewStateLiveData.postValue(it)
                }
//                emitEvent(MoviesEvent.LoadUpcomingMovieError(e))
            }
        }
    }

    fun fetchPopularMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            movieState.copy(loadingPopularMovies = true, loadPopularMoviesError = null).also {
                movieState = it
                viewStateLiveData.postValue(it)
            }
            // load movies
            try {
                movieRepository.fetchPopularMovies()
            } catch (e: Exception) {
                movieState.copy(loadPopularMoviesError = e, loadingPopularMovies = false).also {
                    movieState = it
                    viewStateLiveData.postValue(it)
                }
//                emitEvent(MoviesEvent.LoadPopularMovieError(e))
            }
        }
    }
}