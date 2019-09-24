package com.codigo.movies.app.feature.movies

import androidx.lifecycle.*
import com.codigo.movies.data.model.entity.MovieEntity
import com.codigo.movies.domain.Result
import com.codigo.movies.domain.repository.MovieRepository
import com.codigo.mvi.livedata.MviViewModel
import com.codigo.movies.domain.usecase.GetMoviesUseCase
import com.codigo.movies.domain.usecase.StreamMoviesUseCase
import com.codigo.movies.domain.viewstate.movie.MoviesPartialState
import com.codigo.movies.domain.viewstate.movie.MoviesPartialState.*
import com.codigo.movies.domain.viewstate.movie.MoviesViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.properties.Delegates
import kotlin.properties.Delegates.observable

class MoviesViewModel(
    streamMoviesUseCase: StreamMoviesUseCase,
    private val getMoviesUseCase: GetMoviesUseCase
) : MviViewModel<MoviesViewState, MoviesEvent>() {

    private var viewState = MoviesViewState()

    init {
        // observe movies
        viewStateLiveData.addSource(streamMoviesUseCase(MovieEntity.TYPE_POPULAR)) {
            updateViewState(PopularResult(it))
        }

        viewStateLiveData.addSource(streamMoviesUseCase(MovieEntity.TYPE_UPCOMING)) {
            updateViewState(UpcomingResult(it))
        }

        // first time refresh from server
        fetchPopularMovies()
        fetchUpcomingMovies()

        // init empty view state
        viewStateLiveData.value = MoviesViewState()
    }

    fun fetchPopularMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            // emit loading status
            updateViewState(PopularLoading)

            getMoviesUseCase(MovieEntity.TYPE_POPULAR).either({
                updateViewState(PopularError(it))
            }, {
                updateViewState(PopularLoaded)
            })
        }
    }

    fun fetchUpcomingMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            // emit loading status
            updateViewState(UpcomingLoading)

            getMoviesUseCase(MovieEntity.TYPE_UPCOMING).either({
                updateViewState(UpcomingError(it))
            }, {
                updateViewState(UpcomingLoaded)
            })
        }
    }

    private fun updateViewState(state: MoviesPartialState) {
        viewState = state.reduce(viewState)
        viewStateLiveData.postValue(viewState)
    }
}