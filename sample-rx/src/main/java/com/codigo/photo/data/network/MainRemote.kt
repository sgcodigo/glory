package com.codigo.photo.data.network

import com.codigo.photo.data.model.request.PhotoRequest

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
