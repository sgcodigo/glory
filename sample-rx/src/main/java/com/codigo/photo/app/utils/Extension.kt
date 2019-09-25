package com.codigo.photo.app.utils

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.codigo.photo.R

/**
 * Created by heinhtet on 20,September,2019
 */
fun AppCompatImageView.load(url: String?, circle: Boolean = false) {
    var uri = url
    var requestOptions = RequestOptions()
    var placeHolder = R.drawable.placeholder
    if (uri.isNullOrEmpty()) {
        uri = ""
    }
    if (circle) {
        requestOptions = RequestOptions.circleCropTransform()
        placeHolder = R.drawable.circle_placeholder
    }
    Glide.with(context)
        .load(uri)
        .apply(requestOptions)
        .placeholder(placeHolder)
        .error(placeHolder)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}
