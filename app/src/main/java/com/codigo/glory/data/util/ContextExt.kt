package com.codigo.glory.data.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager


fun Context.isNetworkConnected(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

    return connectivityManager?.run {
        if (Build.VERSION.SDK_INT < 23) {
            activeNetworkInfo?.let {
                it.isConnected && (it.type == ConnectivityManager.TYPE_WIFI ||
                        it.type == ConnectivityManager.TYPE_MOBILE)
            } ?: false

        } else {
            activeNetwork?.let { network ->
                connectivityManager.getNetworkCapabilities(network)?.let {
                    it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                            it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                } ?: false
            } ?: false
        }
    } ?: false
}

fun Context.dpToPx(dp: Float): Int {
    val metrics = resources.displayMetrics
    return (dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
}

fun Context.pxToDp(px: Float): Float {
    return (px / (resources.displayMetrics.densityDpi.toFloat() /
            DisplayMetrics.DENSITY_DEFAULT))
}

fun Context.displayWidth(): Int {
    val displayMetrics = DisplayMetrics()
    (getSystemService(Context.WINDOW_SERVICE) as WindowManager)
        .defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics.widthPixels
}