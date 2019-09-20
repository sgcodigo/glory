package com.codigo.movies.app.di

import androidx.room.Room
import com.codigo.movies.data.db.MovieDatabase
import com.codigo.movies.data.db.dao.MovieDao
import org.koin.dsl.module

val dbModule = module {

    single { MovieDatabase.create(get()) }

    single { get<MovieDatabase>().movieDao() }
}