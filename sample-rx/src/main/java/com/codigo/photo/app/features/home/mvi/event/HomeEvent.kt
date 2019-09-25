package com.codigo.photo.app.features.home.mvi.event

/**
 * Created by heinhtet on 19,September,2019
 */

sealed class HomeEvent {
    data class NetworkError(val throwable: Throwable) : HomeEvent()
}
