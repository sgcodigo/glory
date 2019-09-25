package com.codigo.movies.app.feature.movies

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codigo.movies.MovieIntent
import com.codigo.movies.R
import com.codigo.movies.data.util.gone
import com.codigo.movies.data.util.show
import com.codigo.movies.domain.viewstate.movie.MoviesViewState
import com.codigo.mvi.livedata.MviActivity
import kotlinx.android.synthetic.main.activity_movies.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class MoviesActivity : MviActivity<MoviesViewModel, MoviesViewState, MoviesEvent>() {

    private val moviesViewModel: MoviesViewModel by viewModel()

    private lateinit var popularMovieAdapter: MovieListAdapter
    private lateinit var upcomingMovieAdapter: MovieListAdapter

    override fun setUpLayout() {
        setContentView(R.layout.activity_movies)
        setSupportActionBar(toolbar)

        popularMovieAdapter = MovieListAdapter()
        rvPopular.apply {
            layoutManager = LinearLayoutManager(
                this@MoviesActivity,
                RecyclerView.HORIZONTAL, false
            )
            adapter = popularMovieAdapter
        }

        upcomingMovieAdapter = MovieListAdapter()
        rvUpcoming.apply {
            layoutManager = LinearLayoutManager(
                this@MoviesActivity,
                RecyclerView.HORIZONTAL, false
            )
            adapter = upcomingMovieAdapter
        }

        errorMiniPopular.setErrorText(getString(R.string.error_loading_popular_movies))
        errorMiniUpcoming.setErrorText(getString(R.string.error_loading_upcoming_movies))

        errorMiniPopular.setOnClickListener {
            getViewModel().sendIntent(MovieIntent.RefreshPopularMoviesIntent)
        }

        btnRetryPopular.setOnClickListener {
            getViewModel().sendIntent(MovieIntent.RefreshPopularMoviesIntent)
        }

        errorMiniUpcoming.setOnClickListener {
            getViewModel().sendIntent(MovieIntent.RefreshUpcomingMoviesIntent)
        }

        btnRetryUpcoming.setOnClickListener {
            getViewModel().sendIntent(MovieIntent.RefreshUpcomingMoviesIntent)
        }
    }

    override fun getViewModel() = moviesViewModel

    override fun render(viewState: MoviesViewState) {
        with(viewState) {
            Timber.i("po:${popularMovies.size} and up:${upcomingMovies.size}")
            if (loadingPopularMovies && popularMovies.isNotEmpty()) {
                errorMiniPopular.showLoading()
            } else if (loadingPopularMovies) {
                pbPopularMovie.show()
            } else {
                pbPopularMovie.gone()
                errorMiniPopular.hideLoading()
            }

            if (loadPopularMoviesError != null && popularMovies.isNotEmpty()) {
                errorMiniPopular.showError()
            } else if (loadPopularMoviesError != null) {
                btnRetryPopular.show()
            } else {
                btnRetryPopular.gone()
                errorMiniPopular.hideError()
            }

            if (popularMovies.isNotEmpty()) {
                pbPopularMovie.gone()
                popularMovieAdapter.submitList(popularMovies)
            }

            if (loadingUpcomingMovies && upcomingMovies.isNotEmpty()) {
                errorMiniUpcoming.showLoading()
            } else if (loadingUpcomingMovies) {
                pbUpcomingMovie.show()
            } else {
                pbUpcomingMovie.gone()
                errorMiniUpcoming.hideLoading()
            }

            if (loadUpcomingMoviesError != null && upcomingMovies.isNotEmpty()) {
                errorMiniUpcoming.showError()
            } else if (loadUpcomingMoviesError != null) {
                btnRetryUpcoming.show()
            } else {
                btnRetryUpcoming.gone()
                errorMiniUpcoming.hideError()
            }

            if (upcomingMovies.isNotEmpty()) {
                pbUpcomingMovie.gone()
                upcomingMovieAdapter.submitList(upcomingMovies)
            }
        }
    }

    override fun renderEvent(event: MoviesEvent) {
    }
}
