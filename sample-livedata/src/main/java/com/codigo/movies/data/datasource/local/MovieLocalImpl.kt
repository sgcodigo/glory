package com.codigo.movies.data.datasource.local

import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map
import com.codigo.movies.data.db.dao.MovieDao
import com.codigo.movies.data.model.entity.MovieEntity
import com.codigo.movies.data.toDomain
import com.codigo.movies.data.toEntity
import com.codigo.movies.domain.model.Movie

class MovieLocalImpl(
    private val movieDao: MovieDao
) : MovieLocal {

    override fun insertPopularMovies(movies: List<Movie>) {
        movieDao.insert(movies.toEntity(MovieEntity.TYPE_POPULAR))
    }

    override fun streamPopularMovies() = movieDao.streamMovies(MovieEntity.TYPE_POPULAR)
        .map { it.toDomain() }
        .distinctUntilChanged()

    override fun insertUpcomingMovies(movies: List<Movie>) {
        movieDao.insert(movies.toEntity(MovieEntity.TYPE_UPCOMING))
    }

    override fun streamUpcomingMovies() = movieDao.streamMovies(MovieEntity.TYPE_UPCOMING)
        .map { it.toDomain() }
        .distinctUntilChanged()
}
