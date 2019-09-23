package com.deevvdd.sample_rx.app.features.home.mvi.viewstate

import com.deevvdd.sample_rx.data.model.response.PhotoResponse

/**
 * Created by heinhtet on 19,September,2019
 */

sealed class HomePartialState {
    abstract fun reduce(oldState: HomeViewState): HomeViewState

    object LoadingPhoto : HomePartialState() {
        override fun reduce(oldState: HomeViewState): HomeViewState {
            return oldState.copy(
                loading = true,
                viewMoreLoading = false
            )
        }
    }

    object ViewMoreLoadingPhoto : HomePartialState() {
        override fun reduce(oldState: HomeViewState): HomeViewState {
            return oldState.copy(
                viewMoreLoading = true
            )
        }
    }

    data class PhotoResult(val result: PhotoResponse) : HomePartialState() {
        override fun reduce(oldState: HomeViewState): HomeViewState {
            return oldState.copy(
                loading = false,
                error = null,
                viewMoreLoading = false,
                popularPhotoResult = result
            )
        }
    }


    data class ErrorPhoto(val error: Throwable) : HomePartialState() {
        override fun reduce(oldState: HomeViewState): HomeViewState {
            return oldState.copy(loading = false, error = error, viewMoreLoading = false)
        }
    }
}