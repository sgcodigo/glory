package com.codigo.movies.data.datasource.local

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.distinctUntilChanged
import com.codigo.movies.data.db.dao.MovieDao
import com.codigo.movies.domain.model.Movie
import timber.log.Timber

class MovieLocalImpl(
    private val movieDao: MovieDao
): MovieLocal {

    override fun insertPopularMovies(movies: List<Movie>) {
        movieDao.insert(movies)
    }

    override fun streamPopularMovies() = movieDao.streamMovies(Movie.TYPE_POPULAR).distinctUntilChanged()

    override fun insertUpcomingMovies(movies: List<Movie>) {
        movieDao.insert(movies)
    }

    override fun streamUpcomingMovies() = movieDao.streamMovies(Movie.TYPE_UPCOMING).distinctUntilChanged()

}