package com.codigo.photo.data.model.response

import java.io.Serializable

/**
 * Created by heinhtet on 20,September,2019
 */
data class PhotoResponse(
    val total: Int,
    val totalHints: Int,
    val hits: List<Hit>
)

data class Hit(
    val id: Int,
    val pageURL: String,
    val type: String,
    val tags: String,
    val previewURL: String,
    val previewWidth: Int,
    val previewHeight: Int,
    val webformatURL: String,
    val largeImageURL: String,
    val fullHDURL: String,
    val imageURL: String,
    val imageWidth: Long,
    val imageHeight: Long,
    val imageSize: Long,
    val views: Long,
    val downloads: Long,
    val favorites: Int,
    val likes: Int,
    val comments: Int,
    val user_id: Long,
    val user: String,
    val userImageURL: String
) : Serializable
