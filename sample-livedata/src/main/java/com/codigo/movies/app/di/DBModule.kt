package com.codigo.movies.app.di

import com.codigo.movies.data.db.MovieDatabase
import org.koin.dsl.module

val dbModule = module {

    single { MovieDatabase.create(get()) }

    single { get<MovieDatabase>().movieDao() }
}
