package com.codigo.movies.data.util

import com.codigo.movies.data.exception.DataException
import com.codigo.movies.data.model.response.ErrorResponse
import com.codigo.movies.domain.type.Either
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException

private suspend fun <T, R> Response<T>.handleResponse(
    retrofit: Retrofit,
    fallbackErrorMessage: String,
    mapper: suspend (T) -> R
): Either<DataException, R> {
    getGlobalEvent()?.run {
        return@handleResponse Either.Left(DataException.GlobalError(this))
    }
    val body = body()
    return if (isSuccessful && body != null) {
        withContext(Dispatchers.Default) {
            Either.Right(mapper(body))
        }
    } else {
        parseError(retrofit, fallbackErrorMessage)
    }
}

internal suspend fun <T, R> Retrofit.handleCall(
    fallbackErrorMessage: String,
    apiCall: suspend () -> Response<T>,
    mapper: suspend (T) -> R
) = try {
    apiCall().run { handleResponse(this@handleCall, fallbackErrorMessage) { mapper(it) } }
} catch (e: Exception) {
    Either.Left(e.toIOError())
}

private fun Response<*>.parseError(
    retrofit: Retrofit,
    fallbackErrorMessage: String
): Either.Left<DataException> {
    val converter: Converter<ResponseBody, ErrorResponse> =
        retrofit.responseBodyConverter(ErrorResponse::class.java, arrayOfNulls<Annotation>(0))

    val errorMessage = try {
        errorBody()?.let {
            converter.convert(it)?.statusMessage
        } ?: fallbackErrorMessage
    } catch (e: IOException) {
        fallbackErrorMessage
    }
    return Either.Left(DataException.Api(errorMessage))
}