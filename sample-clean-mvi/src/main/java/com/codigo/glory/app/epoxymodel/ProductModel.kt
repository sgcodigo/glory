package com.codigo.glory.app.epoxymodel

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.Glide
import com.codigo.glory.R
import com.codigo.glory.data.util.displayWidth
import com.codigo.glory.data.util.dpToPx
import com.codigo.glory.domain.model.Product
import kotlinx.android.synthetic.main.model_product.view.*

@EpoxyModelClass(layout = R.layout.model_product)
abstract class ProductModel : EpoxyModelWithHolder<ProductModel.Holder>() {

    @EpoxyAttribute
    lateinit var appleProduct: Product

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var updateFavouriteAction: (Product) -> Unit

    override fun bind(holder: Holder) {
        val product = appleProduct
        var name = ""
        var imageUrl = ""
        var favourite = false
        var shortDescription = ""
        var price = 0.0
        var new = false

        if (product is Product.Mac) {
            name = product.name
            favourite = product.favourite
            imageUrl = product.imageUrl
            shortDescription = product.shortDescription
            price = product.price
            new = product.new
        } else if (product is Product.IPhone) {
            name = product.name
            imageUrl = product.imageUrl
            favourite = product.favourite
            shortDescription = product.shortDescription
            price = product.price
            new = product.new
        }

        holder.context.run {
            val width = (displayWidth() - dpToPx(32f) - dpToPx(20f)) / 2
            holder.vgProductImage.layoutParams.height = width
        }

        Glide.with(holder.context)
                .load(imageUrl)
                .centerCrop()
                .into(holder.ivProduct)

        holder.ivFavourite.setOnClickListener { updateFavouriteAction(product) }
        holder.vgTag.visibility = if (new) View.VISIBLE else View.GONE
        holder.tvName.text = name
        holder.tvDescription.text = shortDescription
        holder.ivFavourite.setImageResource(if (favourite) R.drawable.ic_favorite_active_36dp
        else R.drawable.ic_favorite_inactive_36dp)
        holder.tvPrice.text = price.toString()
    }

    override fun unbind(holder: Holder) {
        holder.ivFavourite.setOnClickListener(null) // prevent momery leak
    }

    inner class Holder : EpoxyHolder() {
        lateinit var context: Context
        lateinit var vgProductImage: ViewGroup
        lateinit var ivProduct: ImageView
        lateinit var vgTag: ViewGroup
        lateinit var tvName: TextView
        lateinit var ivFavourite: ImageView
        lateinit var tvDescription: TextView
        lateinit var tvPrice: TextView

        override fun bindView(itemView: View) {
            context = itemView.context
            vgProductImage = itemView.vgProductImage
            ivProduct = itemView.ivProduct
            vgTag = itemView.vgTag
            tvName = itemView.tvName
            ivFavourite = itemView.ivFavourite
            tvDescription = itemView.tvDescription
            tvPrice = itemView.tvPrice
        }
    }
}