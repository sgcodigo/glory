package com.codigo.movies.app.di

import com.codigo.movies.domain.usecase.GetMoviesUseCase
import com.codigo.movies.domain.usecase.StreamMoviesUseCase
import org.koin.dsl.module

val useCaseModule = module {

    single { GetMoviesUseCase(get()) }

    single { StreamMoviesUseCase(get()) }
}