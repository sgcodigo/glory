package com.codigo.photo.data.repository

import com.codigo.photo.data.model.request.PhotoRequest
import com.codigo.photo.data.model.response.PhotoResponse
import com.codigo.photo.data.network.MainRemote
import io.reactivex.Single

/**
 * Created by heinhtet on 19,September,2019
 */

class MainRepositoryImpl(private val mainRemote: MainRemote) : MainRespository {
    override fun fetchPopularPhoto(photoRequest: PhotoRequest): Single<PhotoResponse> {
        return mainRemote.fetchPopularPhoto(photoRequest)
    }
}
