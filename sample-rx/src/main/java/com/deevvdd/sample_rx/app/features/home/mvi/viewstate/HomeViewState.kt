package com.deevvdd.sample_rx.app.features.home.mvi.viewstate

import com.deevvdd.sample_rx.data.model.response.PhotoResponse

/**
 * Created by heinhtet on 19,September,2019
 */
data class HomeViewState(
    val loading: Boolean = false,
    val popularPhotoResult: PhotoResponse? = null,
    val error: Throwable? = null,
    val viewMoreLoading: Boolean = false
)
