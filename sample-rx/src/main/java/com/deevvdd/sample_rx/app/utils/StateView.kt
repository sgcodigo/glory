package com.deevvdd.sample_rx.app.utils

/**
 * Created by heinhtet on 20,September,2019
 */

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.deevvdd.sample_rx.R
import com.google.android.material.button.MaterialButton

/**
 * Created by heinhtet on 02,September,2019
 */

enum class State {
    Loading,
    Error,
    None,
    TransparentLoading
}

class StateView : RelativeLayout {

    var errorListener: ErrorListener? = null
    private lateinit var tvError: AppCompatTextView
    private lateinit var btnRetry: MaterialButton
    private lateinit var pgLoading: ProgressBar
    private lateinit var view: View
    private lateinit var lyStateContainer: View

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        init()
    }

    fun setErrorMessage(message: String?) {
        this.tvError.text = message
    }

    fun setState(state: State, errorMessage: String? = null, retryEnable: Boolean = false) {
        when (state) {
            State.Loading -> {
                view.show()
                pgLoading.show()
                tvError.hide()
                btnRetry.hide()
                lyStateContainer.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        android.R.color.white
                    )
                )
            }
            State.Error -> {
                view.show()
                this.tvError.text = errorMessage
                pgLoading.hide()
                tvError.show()
                if (retryEnable) {
                    btnRetry.show()
                }
                lyStateContainer.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        android.R.color.white
                    )
                )
            }
            State.None -> {
                view.hide()
                pgLoading.hide()
                tvError.hide()
                btnRetry.hide()
                lyStateContainer.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        android.R.color.white
                    )
                )
            }
            State.TransparentLoading -> {
                view.show()
                pgLoading.show()
                tvError.hide()
                btnRetry.hide()
                lyStateContainer.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        android.R.color.transparent
                    )
                )
            }
        }
    }

    private fun init() {
        view = LayoutInflater.from(context).inflate(R.layout.layout_error_view, this)
        tvError = view.findViewById(R.id.tvErrorView)
        lyStateContainer = view.findViewById(R.id.lyStateContainer)
        pgLoading = view.findViewById(R.id.pbLoading)
        btnRetry = view.findViewById(R.id.btnRetry)
        btnRetry.setOnClickListener { errorListener?.retry() }
        setState(State.None)
    }
}
