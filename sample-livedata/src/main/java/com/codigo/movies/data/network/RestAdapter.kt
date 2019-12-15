package com.codigo.movies.data.network

import com.codigo.movies.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

fun retrofitClient(url: String, httpClient: OkHttpClient): Retrofit = Retrofit.Builder()
    .baseUrl(url)
    .client(httpClient)
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

fun okHttpClient(): OkHttpClient {
    val builder = OkHttpClient.Builder()
    builder.addInterceptor(ResponseInterceptor())
    if (BuildConfig.DEBUG) {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(httpLoggingInterceptor)
    }
    return builder.build()
}
