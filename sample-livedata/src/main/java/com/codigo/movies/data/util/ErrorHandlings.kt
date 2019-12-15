package com.codigo.movies.data.util

import com.codigo.movies.BuildConfig
import com.codigo.movies.data.exception.DataException
import com.codigo.movies.data.exception.GlobalEvent
import retrofit2.Response
import java.io.IOException

fun Throwable.toIOError(): DataException {
    if (BuildConfig.DEBUG) {
        printStackTrace()
    }
    return when (this) {
        is IOException -> DataException.Network
        is DataException -> this
        else -> DataException.Conversion
    }
}

fun <T> Response<T>.getGlobalEvent(): GlobalEvent? {
    return code().codeToError()
}

fun Int.codeToError(): GlobalEvent? {
    return when (this) {
        500 -> {
            GlobalEvent.ServerError()
        }
        503 -> {
            GlobalEvent.Maintenance()
        }
        403 -> {
            GlobalEvent.Forbidden()
        }
        else -> null
    }
}
