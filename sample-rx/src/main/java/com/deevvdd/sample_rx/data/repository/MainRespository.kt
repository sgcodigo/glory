package com.deevvdd.sample_rx.data.repository

import com.deevvdd.sample_rx.data.model.response.PhotoResponse
import io.reactivex.Single

/**
 * Created by heinhtet on 19,September,2019
 */
interface MainRespository {
    fun fetchPopularPhoto(page: Int): Single<PhotoResponse>
    fun fetchLatestPhoto(page: Int): Single<PhotoResponse>
}