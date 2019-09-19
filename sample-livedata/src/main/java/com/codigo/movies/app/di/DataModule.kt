package com.codigo.movies.app.di

import com.codigo.movies.data.datasource.local.MovieLocal
import com.codigo.movies.data.datasource.local.MovieLocalImpl
import com.codigo.movies.data.datasource.remote.MovieRemote
import com.codigo.movies.data.datasource.remote.MovieRemoteImpl
import com.codigo.movies.data.network.okHttpClient
import com.codigo.movies.data.network.retrofitClient
import com.codigo.movies.data.network.service.MovieService
import com.codigo.movies.data.repository.MovieRepositoryImpl
import com.codigo.movies.domain.MovieRepository
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