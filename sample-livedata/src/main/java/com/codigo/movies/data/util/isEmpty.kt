package com.codigo.movies.data.util

import android.graphics.Bitmap
import android.view.View
import com.google.android.material.snackbar.Snackbar
import java.io.ByteArrayOutputStream

fun View.snack(text: String, duration: Int = Snackbar.LENGTH_SHORT, actionText: String = "", action: () -> Unit = {}) {
    Snackbar.make(this, text, duration).setAction(actionText) { action() }.show()
}

fun View.snackForever(text: String, actionText: String = "", action: () -> Unit = {}) {
    Snackbar.make(this, text, Snackbar.LENGTH_INDEFINITE).setAction(actionText) { action() }.show()
}

fun View.gone() {
    visibility = View.GONE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.enable() {
    this.isEnabled = true
}

fun View.disable() {
    this.isEnabled = false
}