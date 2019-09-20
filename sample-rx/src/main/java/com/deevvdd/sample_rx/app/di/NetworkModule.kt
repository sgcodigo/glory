package com.deevvdd.sample_rx.app.di

import com.deevvdd.sample_rx.data.network.ApiService
import com.deevvdd.sample_rx.data.network.MainRemote
import com.deevvdd.sample_rx.data.network.createNetworkClient
import com.deevvdd.sample_rx.data.network.httpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import kotlin.math.sin

/**
 * Created by heinhtet on 19,September,2019
 */


val networkModule = module {
    single { httpClient(get()) }
    single { createNetworkClient(get()) }
    single { provideApiService(get()) }
    single { MainRemote(get()) }
}
fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

