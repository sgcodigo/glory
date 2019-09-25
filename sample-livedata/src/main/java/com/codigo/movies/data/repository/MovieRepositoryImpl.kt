package com.codigo.movies.data.repository

import androidx.lifecycle.LiveData
import com.codigo.movies.data.datasource.local.MovieLocal
import com.codigo.movies.data.datasource.remote.MovieRemote
import com.codigo.movies.domain.Either
import com.codigo.movies.domain.model.Movie
import com.codigo.movies.domain.repository.MovieRepository

class MovieRepositoryImpl(
    private val local: MovieLocal,
    private val remote: MovieRemote
) : MovieRepository {

    override fun streamUpcomingMovies() = local.streamUpcomingMovies()

    override fun streamPopularMovies() = local.streamPopularMovies()

    override suspend fun fetchPopularMovies() = remote.fetchPopularMovies().also {
        it.either({}, { local.insertPopularMovies(it) })
    }

    override suspend fun fetchUpcomingMovies() = remote.fetchUpcomingMovies().also {
        it.either({}, { local.insertUpcomingMovies(it) })
    }
}
