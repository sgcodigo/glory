package com.codigo.photo.data.repository

import com.codigo.photo.data.model.request.PhotoRequest
import com.codigo.photo.data.model.response.PhotoResponse
import io.reactivex.Single

/**
 * Created by heinhtet on 19,September,2019
 */
interface MainRespository {
    fun fetchPopularPhoto(photoRequest: PhotoRequest): Single<PhotoResponse>
}
