package com.codigo.photo.app.di

import com.codigo.photo.data.repository.MainRepositoryImpl
import org.koin.dsl.module

/**
 * Created by heinhtet on 19,September,2019
 */
val repositoryModule = module {
    single { MainRepositoryImpl(get()) }
}
