package com.codigo.photo.app.features.home.mvi.viewstate

import com.codigo.photo.data.model.response.PhotoResponse

/**
 * Created by heinhtet on 19,September,2019
 */
data class HomeViewState(
    val loading: Boolean = false,
    val popularPhotoResult: PhotoResponse? = null,
    val error: Throwable? = null,
    val viewMoreLoading: Boolean = false
)
