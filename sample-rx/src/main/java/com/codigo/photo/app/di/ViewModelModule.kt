package com.codigo.photo.app.di

import com.codigo.photo.app.features.home.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by heinhtet on 19,September,2019
 */
val viewModelModule = module {
    viewModel { HomeViewModel(get(), get()) }
}
