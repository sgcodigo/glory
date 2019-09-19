package com.example.sample_mvi_coroutine.app.di

import com.example.sample_mvi_coroutine.app.feature.movies.MoviesViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { MoviesViewModel(get()) }
}