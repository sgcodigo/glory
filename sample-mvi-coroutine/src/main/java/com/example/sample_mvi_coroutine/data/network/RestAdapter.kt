package com.example.sample_mvi_coroutine.data.network

import com.example.sample_mvi_coroutine.BuildConfig
import com.example.sample_mvi_coroutine.data.util.MOVIE_BASE_URL
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