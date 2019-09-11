package com.codigo.glory.app.epoxymodel

import android.view.View
import android.widget.Button
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.codigo.glory.R
import kotlinx.android.synthetic.main.model_error.view.*

@EpoxyModelClass(layout = R.layout.model_error)
abstract class ErrorModel : EpoxyModelWithHolder<ErrorModel.Holder>() {

    @EpoxyAttribute
    lateinit var errorMessage: String

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var retryAction: () -> Unit

    override fun bind(holder: Holder) {
        holder.tvError.text = errorMessage
        holder.btnRetry.setOnClickListener { retryAction() }
    }

    override fun unbind(holder: Holder) {
        holder.btnRetry.setOnClickListener(null) // prevent memory leak
    }

    inner class Holder : EpoxyHolder() {
        lateinit var tvError: TextView
        lateinit var btnRetry: Button

        override fun bindView(itemView: View) {
            tvError = itemView.tvError
            btnRetry = itemView.btnRetry
        }
    }
}