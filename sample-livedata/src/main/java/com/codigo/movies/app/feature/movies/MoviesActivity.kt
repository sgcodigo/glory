package com.codigo.movies.app.feature.movies

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codigo.mvi.livedata.MviActivity
import com.codigo.movies.R
import com.codigo.movies.domain.viewstate.movie.MoviesViewState
import kotlinx.android.synthetic.main.activity_movies.*
import org.koin.android.viewmodel.ext.android.viewModel

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

        btnRetryPopular.setOnClickListener {
            getViewModel().fetchPopularMovies()
        }

        btnRetryUpcoming.setOnClickListener {
            getViewModel().fetchUpcomingMovies()
        }
    }

    override fun getViewModel() = moviesViewModel

    override fun render(viewState: MoviesViewState) {

        if (viewState.loadingPopularMovies) {
            btnRetryPopular.visibility = View.GONE
            pbPopularMovie.visibility = View.VISIBLE
        } else {
            pbPopularMovie.visibility = View.GONE
        }

        if (viewState.popularMovies.isNotEmpty()) {
            btnRetryPopular.visibility = View.GONE
            popularMovieAdapter.submitList(viewState.popularMovies)
        }

        viewState.loadPopularMoviesError?.also {
            pbPopularMovie.visibility = View.GONE
            btnRetryPopular.visibility = View.VISIBLE
        }

        if (viewState.loadingUpcomingMovies) {
            btnRetryUpcoming.visibility = View.GONE
            pbUpcomingMovie.visibility = View.VISIBLE
        } else {
            pbUpcomingMovie.visibility = View.GONE
        }

        if (viewState.upcomingMovies.isNotEmpty()) {
            btnRetryUpcoming.visibility = View.GONE
            upcomingMovieAdapter.submitList(viewState.upcomingMovies)
        }

        viewState.loadUpcomingMoviesError?.also {
            pbUpcomingMovie.visibility = View.GONE
            btnRetryUpcoming.visibility = View.VISIBLE
        }
    }

    override fun renderEvent(event: MoviesEvent) {
//        when(event) {
//            is MoviesEvent.LoadUpcomingMovieError -> {
//                btnRetryUpcoming.visibility = View.VISIBLE
//            }
//            is MoviesEvent.LoadPopularMovieError -> {
//                btnRetryPopular.visibility = View.VISIBLE
//            }
//        }
    }
}
