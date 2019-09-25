package com.deevvdd.sample_rx.data.model.request

/**
 * Created by Hein Htet on 2019-09-21.
 */
data class PhotoRequest(
    val page: Int,
    val categoryName: String,
    val orderBy: String,
    val isRefreshedAll: Boolean
)
