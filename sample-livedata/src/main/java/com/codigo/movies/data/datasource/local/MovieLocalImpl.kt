package com.codigo.movies.data.datasource.local

import androidx.lifecycle.MutableLiveData
import com.codigo.movies.domain.model.Movie

class MovieLocalImpl: MovieLocal {

    private val upcomingMovies = MutableLiveData<List<Movie>>()
    private val popularMovies = MutableLiveData<List<Movie>>()

    override fun insertPopularMovies(movies: List<Movie>) {
        popularMovies.postValue(movies)
    }

    override fun streamPopularMovies() = popularMovies

    override fun insertUpcomingMovies(movies: List<Movie>) {
        upcomingMovies.postValue(movies)
    }

    override fun streamUpcomingMovies() = upcomingMovies

}