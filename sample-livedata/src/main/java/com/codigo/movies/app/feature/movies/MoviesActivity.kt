package com.codigo.movies.app.feature.movies

import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codigo.movies.R
import com.codigo.movies.app.extension.gone
import com.codigo.movies.app.extension.show
import com.codigo.movies.app.feature.movies.viewstate.MoviesViewState
import com.codigo.mvi.livedata.MviActivity
import kotlinx.android.synthetic.main.activity_movies.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class MoviesActivity : MviActivity<MoviesViewModel, MoviesViewState, MoviesEvent>() {

    private val moviesViewModel: MoviesViewModel by viewModel()

    private lateinit var popularMovieAdapter: MovieListAdapter
    private lateinit var upcomingMovieAdapter: MovieListAdapter

    private val offlineAlertDialog by lazy {
        AlertDialog.Builder(this)
            .setTitle(R.string.alert_title_offline)
            .setMessage(R.string.alert_msg_offline)
            .setPositiveButton(R.string.alert_dismiss, null)
            .show()
    }

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

            btnRetryPopular.text = loadPopularMoviesError?.localizedMessage ?: ""
            errorMiniPopular.setErrorText(loadPopularMoviesError?.localizedMessage ?: "")

            btnRetryUpcoming.text = loadUpcomingMoviesError?.localizedMessage ?: ""
            errorMiniUpcoming.setErrorText(loadUpcomingMoviesError?.localizedMessage ?: "")

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
        when (event) {
            is MoviesEvent.OfflineEvent -> {
                if (!offlineAlertDialog.isShowing) {
                    offlineAlertDialog.show()
                }
            }
        }
    }
}
