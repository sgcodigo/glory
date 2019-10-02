package com.codigo.movies.app

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.codigo.movies.R
import com.codigo.movies.data.util.gone
import com.codigo.movies.data.util.show
import kotlinx.android.synthetic.main.layout_error_mini.view.*

class MiniErrorView : FrameLayout {

    var onRetryClicked: (() -> Unit)? = null

    constructor(context: Context) : super(context) {
        inflateView()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        inflateView()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        inflateView()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        setOnClickListener { onRetryClicked?.invoke() }
    }

    fun showLoading() {
        vgError.gone()
        pbMini.show()
    }

    fun hideLoading() {
        pbMini.gone()
    }

    fun setErrorText(msg: String) {
        tvErrorMsg.text = msg
    }

    fun showError() {
        pbMini.gone()
        vgError.show()
    }

    fun hideError() {
        vgError.gone()
    }

    fun setRetryClickListener(onRetryClicked: () -> Unit) {
        this.onRetryClicked = onRetryClicked
    }

    private fun inflateView() {
        LayoutInflater.from(context)
            .inflate(R.layout.layout_error_mini, this, false)
            .also { addView(it) }
    }
}
