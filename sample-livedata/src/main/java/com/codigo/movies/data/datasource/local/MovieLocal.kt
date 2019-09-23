package com.codigo.movies.data.datasource.local

import androidx.lifecycle.LiveData
import com.codigo.movies.data.model.entity.MovieEntity
import com.codigo.movies.domain.model.Movie

interface MovieLocal {

    fun streamPopularMovies(): LiveData<List<Movie>>
    fun insertPopularMovies(movies: List<Movie>)

    fun streamUpcomingMovies(): LiveData<List<Movie>>
    fun insertUpcomingMovies(movies: List<Movie>)
}