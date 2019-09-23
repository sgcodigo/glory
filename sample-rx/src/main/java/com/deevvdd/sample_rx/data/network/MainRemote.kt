package com.deevvdd.sample_rx.data.network

import com.deevvdd.sample_rx.data.model.request.PhotoRequest

/**
 * Created by heinhtet on 19,September,2019
 */
class MainRemote(private val apiService: ApiService) {

    fun fetchPopularPhoto(photoRequest: PhotoRequest) =
        apiService.fetchPhoto(
            "photo",
            "",
            photoRequest.page,
            20,
            photoRequest.orderBy,
            photoRequest.categoryName
        )
}