package com.deevvdd.sample_rx.data.repository

import com.deevvdd.sample_rx.data.model.response.PhotoResponse
import com.deevvdd.sample_rx.data.network.MainRemote
import io.reactivex.Single

/**
 * Created by heinhtet on 19,September,2019
 */

class MainRepositoryImpl(private val mainRemote: MainRemote) : MainRespository {
    override fun fetchLatestPhoto(page: Int): Single<PhotoResponse> {
        return mainRemote.fetchLatestPhoto(page, 20)
    }

    override fun fetchPopularPhoto(page: Int): Single<PhotoResponse> {
        return mainRemote.fetchPopularPhoto(page, 20)
    }
}