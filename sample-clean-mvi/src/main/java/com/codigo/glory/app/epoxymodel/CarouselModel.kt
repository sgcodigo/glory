package com.codigo.glory.app.epoxymodel

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.ModelView

@ModelView(saveViewState = true, autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class CarouselModel(context: Context?) : Carousel(context) {

    override fun createLayoutManager(): LayoutManager {
        return LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
        )
    }
}