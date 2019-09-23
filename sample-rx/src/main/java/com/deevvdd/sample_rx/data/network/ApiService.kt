package com.deevvdd.sample_rx.data.network

import com.deevvdd.sample_rx.data.model.response.PhotoResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by heinhtet on 19,September,2019
 */
interface ApiService {
    @GET(KEY)
    fun fetchPhoto(
        @Query("image_type") imageType: String,
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("order") order: String,
        @Query("category") category: String
    ): Single<PhotoResponse>
}