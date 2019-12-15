package com.codigo.movies.app.feature.movies

import androidx.lifecycle.viewModelScope
import com.codigo.movies.app.feature.movies.MovieIntent.*
import com.codigo.movies.app.feature.movies.viewstate.MoviesPartialState
import com.codigo.movies.app.feature.movies.viewstate.MoviesPartialState.*
import com.codigo.movies.app.feature.movies.viewstate.MoviesViewState
import com.codigo.movies.data.exception.DataException
import com.codigo.movies.data.model.entity.MovieEntity
import com.codigo.movies.domain.usecase.GetMoviesUseCase
import com.codigo.movies.domain.usecase.StreamMoviesUseCase
import com.codigo.mvi.livedata.MviViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val streamMoviesUseCase: StreamMoviesUseCase,
    private val getMoviesUseCase: GetMoviesUseCase
) : MviViewModel<MoviesViewState, MoviesEvent, MovieIntent>() {

    private var viewState = MoviesViewState()

    init {
        sendIntent(StreamPopularMoviesIntent)
        sendIntent(StreamUpcomingMoviesIntent)
        sendIntent(RefreshPopularMoviesIntent)
        sendIntent(RefreshUpcomingMoviesIntent)
    }

    override fun sendIntent(intent: MovieIntent) {
        when (intent) {
            is StreamPopularMoviesIntent -> streamPopularMovies()
            is StreamUpcomingMoviesIntent -> streamUpcomingMovies()
            is RefreshPopularMoviesIntent -> fetchPopularMovies()
            is RefreshUpcomingMoviesIntent -> fetchUpcomingMovies()
        }
    }

    private fun streamPopularMovies() {
        viewModelScope.launch {
            streamMoviesUseCase(MovieEntity.TYPE_POPULAR)
                .collect {
                    updateViewState(PopularResult(it))
                }
        }
    }

    private fun streamUpcomingMovies() {
        viewModelScope.launch {
            streamMoviesUseCase(MovieEntity.TYPE_UPCOMING)
                .collect {
                    updateViewState(UpcomingResult(it))
                }
        }
    }

    private fun fetchPopularMovies() {
        viewModelScope.launch {
            // emit loading status
            updateViewState(PopularLoading)

            getMoviesUseCase(MovieEntity.TYPE_POPULAR).either({
                updateViewState(PopularError(it))
                if (it is DataException.Network) {
                    emitEvent(MoviesEvent.OfflineEvent)
                }
            }, {
                updateViewState(PopularLoaded)
            })
        }
    }

    private fun fetchUpcomingMovies() {
        viewModelScope.launch {
            // emit loading status
            updateViewState(UpcomingLoading)

            getMoviesUseCase(MovieEntity.TYPE_UPCOMING).either({
                updateViewState(UpcomingError(it))
                emitEvent(MoviesEvent.OfflineEvent)
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
