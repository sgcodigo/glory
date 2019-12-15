package com.codigo.movies.data.datasource.local

import com.codigo.movies.data.db.dao.MovieDao
import com.codigo.movies.data.mapper.toDomain
import com.codigo.movies.data.mapper.toEntity
import com.codigo.movies.data.model.entity.MovieEntity
import com.codigo.movies.domain.model.Movie
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class MovieLocalImpl(
    private val movieDao: MovieDao
) : MovieLocal {

    override suspend fun insertPopularMovies(movies: List<Movie>) {
        movieDao.insert(movies.toEntity(MovieEntity.TYPE_POPULAR))
    }

    @ExperimentalCoroutinesApi
    override fun streamPopularMovies() = movieDao.streamMovies(MovieEntity.TYPE_POPULAR)
        .map { it.toDomain() }
        .distinctUntilChanged()

    override suspend fun insertUpcomingMovies(movies: List<Movie>) {
        movieDao.insert(movies.toEntity(MovieEntity.TYPE_UPCOMING))
    }

    @ExperimentalCoroutinesApi
    override fun streamUpcomingMovies() = movieDao.streamMovies(MovieEntity.TYPE_UPCOMING)
        .map { it.toDomain() }
        .distinctUntilChanged()
}
