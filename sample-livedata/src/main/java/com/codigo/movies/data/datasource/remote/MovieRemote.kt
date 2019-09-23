package com.codigo.movies.data.datasource.remote

import com.codigo.movies.data.db.dao.MovieDao
import com.codigo.movies.data.model.data.MovieData
import com.codigo.movies.domain.model.Movie

interface MovieRemote {

    suspend fun fetchPopularMovies(): List<Movie>

    suspend fun fetchUpcomingMovies(): List<Movie>
}