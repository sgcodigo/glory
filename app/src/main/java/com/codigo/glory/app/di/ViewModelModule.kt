package com.codigo.glory.app.di

import com.codigo.glory.app.feature.products.ProductsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by khunzohn on 2019-09-11 at Codigo
 */

val viewModelModule = module {

    viewModel { ProductsViewModel(get()) }
}