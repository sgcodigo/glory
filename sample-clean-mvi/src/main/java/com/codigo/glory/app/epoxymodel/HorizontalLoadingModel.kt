package com.codigo.glory.app.epoxymodel

import android.view.View
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.codigo.glory.R

@EpoxyModelClass(layout = R.layout.model_horizontal_loading)
abstract class HorizontalLoadingModel : EpoxyModelWithHolder<HorizontalLoadingModel.Holder>() {

    inner class Holder : EpoxyHolder() {
        override fun bindView(itemView: View) {
            // nothing to do
        }
    }
}
