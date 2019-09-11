package com.codigo.glory.app.epoxymodel

import android.view.View
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.codigo.glory.R
import kotlinx.android.synthetic.main.model_horizontal_error.view.*

@EpoxyModelClass(layout = R.layout.model_horizontal_error)
abstract class HorizontalErrorModel : EpoxyModelWithHolder<HorizontalErrorModel.Holder>() {

    @EpoxyAttribute
    lateinit var errorMessage: String

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var retryListener: () -> Unit

    override fun bind(holder: Holder) {
        holder.tvHorizontalError.text = errorMessage
        holder.tvHorizontalRetry.setOnClickListener { retryListener() }
    }

    override fun unbind(holder: Holder) {
        holder.tvHorizontalRetry.setOnClickListener(null)
    }

    inner class Holder : EpoxyHolder() {
        lateinit var tvHorizontalError: TextView
        lateinit var tvHorizontalRetry: TextView
        override fun bindView(itemView: View) {
            tvHorizontalError = itemView.tvHorizontalError
            tvHorizontalRetry = itemView.tvHorizontalRetry
        }
    }
}
