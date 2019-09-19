package com.example.sample_mvi_coroutine.data.repository

import androidx.lifecycle.LiveData
import com.example.sample_mvi_coroutine.data.datasource.local.MovieLocal
import com.example.sample_mvi_coroutine.data.datasource.remote.MovieRemote
import com.example.sample_mvi_coroutine.domain.MovieRepository
import com.example.sample_mvi_coroutine.domain.model.Movie

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