package com.codigo.movies.data.network

import com.codigo.movies.BuildConfig
import com.codigo.movies.data.util.MOVIE_BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

fun retrofitClient(httpClient: OkHttpClient): Retrofit = Retrofit.Builder()
    .baseUrl(MOVIE_BASE_URL)
    .client(httpClient)
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

fun okHttpClient(): OkHttpClient {
    val builder = OkHttpClient.Builder()
    if (BuildConfig.DEBUG) {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(httpLoggingInterceptor)
    }
    return builder.build()
}
