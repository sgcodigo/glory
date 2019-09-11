package com.codigo.glory.app.di

import com.codigo.glory.data.datasource.cache.IPhoneCache
import com.codigo.glory.data.datasource.cache.MacCache
import com.codigo.glory.data.datasource.remote.GsonProductRemote
import com.codigo.glory.data.datasource.remote.ProductRemote
import com.codigo.glory.data.repository.ProductRepositoryImpl
import com.codigo.glory.data.service.GsonProductService
import com.codigo.glory.domain.repository.ProductRepository
import org.koin.dsl.module

/**
 * Created by khunzohn on 2019-09-11 at Codigo
 */
val dataModule = module {

    single { GsonProductService(get()) }

    single<ProductRemote> { GsonProductRemote(get(), get(), get()) }

    single { IPhoneCache(get(), get()) }

    single { MacCache(get(), get()) }

    single<ProductRepository> { ProductRepositoryImpl(get(), get(), get()) }
}