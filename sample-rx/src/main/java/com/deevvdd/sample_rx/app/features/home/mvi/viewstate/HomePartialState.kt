package com.deevvdd.sample_rx.app.features.home.mvi.viewstate

import com.deevvdd.sample_rx.data.model.response.PhotoResponse

/**
 * Created by heinhtet on 19,September,2019
 */

sealed class HomePartialState {
    abstract fun reduce(oldState: HomeViewState): HomeViewState

    object LoadingPopularPhoto : HomePartialState() {
        override fun reduce(oldState: HomeViewState): HomeViewState {
            return oldState.copy(
                loading = true
            )
        }
    }

    data class PopularPhotoResult(val result: PhotoResponse) : HomePartialState() {
        override fun reduce(oldState: HomeViewState): HomeViewState {
            return oldState.copy(
                loading = false,
                error = null,
                popularPhotoResult = result
            )
        }
    }


    data class ErrorPopularPhoto(val error: Throwable) : HomePartialState() {
        override fun reduce(oldState: HomeViewState): HomeViewState {
            return oldState.copy(loading = false, error = error)
        }
    }
}