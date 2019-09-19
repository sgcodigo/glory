package com.codigo.movies.app.di

import com.codigo.movies.app.feature.movies.MoviesViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { MoviesViewModel(get()) }
}