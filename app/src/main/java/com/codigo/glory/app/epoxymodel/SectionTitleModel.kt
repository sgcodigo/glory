package com.codigo.glory.app.epoxymodel

import android.view.View
import android.widget.Button
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.codigo.glory.R
import kotlinx.android.synthetic.main.model_section_title.view.*

@EpoxyModelClass(layout = R.layout.model_section_title)
abstract class SectionTitleModel : EpoxyModelWithHolder<SectionTitleModel.Holder>() {

    @EpoxyAttribute
    lateinit var sectionTitle: String

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var seeAllAction: () -> Unit

    override fun bind(holder: Holder) {
        holder.tvSectionTitle.text = sectionTitle
        holder.btnSeeAll.setOnClickListener { seeAllAction() }
    }

    override fun unbind(holder: Holder) {
        holder.btnSeeAll.setOnClickListener(null) // prevent memory leak
    }

    inner class Holder : EpoxyHolder() {
        lateinit var btnSeeAll: Button
        lateinit var tvSectionTitle: TextView
        override fun bindView(itemView: View) {
            tvSectionTitle = itemView.tvSectionTitle
            btnSeeAll = itemView.btnSeeAll
        }
    }
}