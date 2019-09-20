package com.deevvdd.sample_rx.app.di

import com.deevvdd.sample_rx.data.repository.MainRepositoryImpl
import org.koin.dsl.module

/**
 * Created by heinhtet on 19,September,2019
 */
val repositoryModule = module {
    single { MainRepositoryImpl(get()) }
}