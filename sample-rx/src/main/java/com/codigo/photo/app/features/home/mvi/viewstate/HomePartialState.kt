package com.codigo.photo.app.features.home.mvi.viewstate

import com.codigo.photo.app.utils.EMPTY
import com.codigo.photo.app.utils.LOADING
import com.codigo.photo.data.model.response.Hit
import com.codigo.photo.data.model.response.PhotoResponse

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

    data class PhotoResult(val result: PhotoResponse, val page: Int) : HomePartialState() {
        override fun reduce(oldState: HomeViewState): HomeViewState {
            var photos: ArrayList<Hit> = ArrayList(result.hits)
            if (page > 1) {
                var type: String = if (result.hits.isNotEmpty())
                    LOADING
                else
                    EMPTY
                val emptyState = Hit(
                    -1,
                    "",
                    type,
                    "",
                    "",
                    0,
                    0,
                    "",
                    "",
                    "",
                    "",
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    "",
                    ""
                )
                photos.add(emptyState)
            }
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
