package com.codigo.glory.app.di

import com.codigo.glory.domain.interactor.ProductsInteractor
import org.koin.dsl.module

/**
 * Created by khunzohn on 2019-09-11 at Codigo
 */
val interactorModule = module {
    factory { ProductsInteractor(get(), get(), get()) }
}