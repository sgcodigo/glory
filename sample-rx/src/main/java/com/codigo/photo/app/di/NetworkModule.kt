package com.codigo.photo.app.di

import com.codigo.photo.data.network.ApiService
import com.codigo.photo.data.network.MainRemote
import com.codigo.photo.data.network.createNetworkClient
import com.codigo.photo.data.network.httpClient
import org.koin.dsl.module
import retrofit2.Retrofit

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
