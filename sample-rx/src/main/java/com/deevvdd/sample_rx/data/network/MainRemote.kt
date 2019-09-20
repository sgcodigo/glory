package com.deevvdd.sample_rx.data.network

/**
 * Created by heinhtet on 19,September,2019
 */
class MainRemote(private val apiService: ApiService) {

    fun fetchPopularPhoto(page: Int, perPage: Int) =
        apiService.fetchPhoto("photo", "", page, perPage, "popular")


    fun fetchLatestPhoto(page: Int, perPage: Int) =
        apiService.fetchPhoto("photo", "", page, perPage, "latest")
}