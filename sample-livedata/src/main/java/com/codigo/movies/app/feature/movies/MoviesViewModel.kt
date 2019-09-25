package com.codigo.movies.app.feature.movies

import androidx.lifecycle.viewModelScope
import com.codigo.movies.MovieIntent
import com.codigo.movies.MovieIntent.RefreshPopularMoviesIntent
import com.codigo.movies.MovieIntent.RefreshUpcomingMoviesIntent
import com.codigo.movies.MovieIntent.StreamPopularMoviesIntent
import com.codigo.movies.MovieIntent.StreamUpcomingMoviesIntent
import com.codigo.movies.data.model.entity.MovieEntity
import com.codigo.movies.domain.usecase.GetMoviesUseCase
import com.codigo.movies.domain.usecase.StreamMoviesUseCase
import com.codigo.movies.domain.viewstate.movie.MoviesPartialState
import com.codigo.movies.domain.viewstate.movie.MoviesPartialState.PopularError
import com.codigo.movies.domain.viewstate.movie.MoviesPartialState.PopularLoaded
import com.codigo.movies.domain.viewstate.movie.MoviesPartialState.PopularLoading
import com.codigo.movies.domain.viewstate.movie.MoviesPartialState.PopularResult
import com.codigo.movies.domain.viewstate.movie.MoviesPartialState.UpcomingError
import com.codigo.movies.domain.viewstate.movie.MoviesPartialState.UpcomingLoaded
import com.codigo.movies.domain.viewstate.movie.MoviesPartialState.UpcomingLoading
import com.codigo.movies.domain.viewstate.movie.MoviesPartialState.UpcomingResult
import com.codigo.movies.domain.viewstate.movie.MoviesViewState
import com.codigo.mvi.livedata.MviViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okio.IOException

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
        viewStateLiveData.addSource(streamMoviesUseCase(MovieEntity.TYPE_POPULAR)) {
            updateViewState(PopularResult(it))
        }
    }

    private fun streamUpcomingMovies() {
        viewStateLiveData.addSource(streamMoviesUseCase(MovieEntity.TYPE_UPCOMING)) {
            updateViewState(UpcomingResult(it))
        }
    }

    private fun fetchPopularMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            // emit loading status
            updateViewState(PopularLoading)

            getMoviesUseCase(MovieEntity.TYPE_POPULAR).either({
                updateViewState(PopularError(it))
                if (it is IOException) {
                    emitEvent(MoviesEvent.OfflineEvent)
                }
            }, {
                updateViewState(PopularLoaded)
            })
        }
    }

    private fun fetchUpcomingMovies() {
        viewModelScope.launch(Dispatchers.IO) {
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
