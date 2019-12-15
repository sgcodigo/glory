package com.codigo.movies.app.di

import com.codigo.movies.data.datasource.local.MovieLocal
import com.codigo.movies.data.datasource.local.MovieLocalImpl
import com.codigo.movies.data.datasource.remote.MovieRemote
import com.codigo.movies.data.datasource.remote.MovieRemoteImpl
import com.codigo.movies.data.network.okHttpClient
import com.codigo.movies.data.network.retrofitClient
import com.codigo.movies.data.network.service.MovieService
import com.codigo.movies.data.repository.MovieRepositoryImpl
import com.codigo.movies.data.util.MOVIE_BASE_URL
import com.codigo.movies.domain.repository.MovieRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val dataModule = module {

    single { retrofitClient(get(named("movieBaseUrl")), get()) }

    single(named("movieBaseUrl")) { MOVIE_BASE_URL }

    single { okHttpClient() }

    single {
        val retrofit: Retrofit = get()
        retrofit.create(MovieService::class.java)
    }

    single<MovieRepository> { MovieRepositoryImpl(get(), get()) }

    single<MovieRemote> { MovieRemoteImpl(get(), get()) }

    single<MovieLocal> { MovieLocalImpl(get()) }
}
