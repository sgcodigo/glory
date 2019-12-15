package com.codigo.movies.data.network

import com.codigo.movies.data.exception.GlobalEventStream
import com.codigo.movies.data.util.codeToError
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.Interceptor
import okhttp3.Response

class ResponseInterceptor : Interceptor {

    @ExperimentalCoroutinesApi
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        response.code().codeToError()?.let {
            GlobalEventStream.emit(it)
        }

        return response
    }
}