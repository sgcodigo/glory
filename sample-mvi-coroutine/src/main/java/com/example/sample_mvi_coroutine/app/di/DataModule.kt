package com.example.sample_mvi_coroutine.app.di

import com.example.sample_mvi_coroutine.data.datasource.local.MovieLocal
import com.example.sample_mvi_coroutine.data.datasource.local.MovieLocalImpl
import com.example.sample_mvi_coroutine.data.datasource.remote.MovieRemote
import com.example.sample_mvi_coroutine.data.datasource.remote.MovieRemoteImpl
import com.example.sample_mvi_coroutine.data.network.okHttpClient
import com.example.sample_mvi_coroutine.data.network.retrofitClient
import com.example.sample_mvi_coroutine.data.network.service.MovieService
import com.example.sample_mvi_coroutine.data.repository.MovieRepositoryImpl
import com.example.sample_mvi_coroutine.domain.MovieRepository
import org.koin.dsl.module
import retrofit2.Retrofit

val dataModule = module {

    single { retrofitClient(get()) }

    single { okHttpClient() }

    single {
        val retrofit: Retrofit = get()
        retrofit.create(MovieService::class.java)
    }

    single<MovieRepository> { MovieRepositoryImpl(get(), get()) }

    single<MovieRemote> { MovieRemoteImpl(get()) }

    single<MovieLocal> { MovieLocalImpl() }
}