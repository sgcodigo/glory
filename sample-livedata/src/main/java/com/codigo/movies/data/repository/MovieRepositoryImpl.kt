package com.codigo.movies.data.repository

import androidx.lifecycle.LiveData
import com.codigo.movies.data.datasource.local.MovieLocal
import com.codigo.movies.data.datasource.remote.MovieRemote
import com.codigo.movies.domain.repository.MovieRepository
import com.codigo.movies.domain.model.Movie

class MovieRepositoryImpl(
    private val local: MovieLocal,
    private val remote: MovieRemote
): MovieRepository {

    override fun streamUpcomingMovies(): LiveData<List<Movie>> {
        return local.streamUpcomingMovies()
    }

    override fun streamPopularMovies(): LiveData<List<Movie>> {
        return local.streamPopularMovies()
    }

    override suspend fun fetchPopularMovies(): List<Movie> {
        return remote.fetchPopularMovies().also {
            local.insertPopularMovies(it)
        }
    }

    override suspend fun fetchUpcomingMovies(): List<Movie> {
        return remote.fetchUpcomingMovies().also {
            local.insertUpcomingMovies(it)
        }
    }
}