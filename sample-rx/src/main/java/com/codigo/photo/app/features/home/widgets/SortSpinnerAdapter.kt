package com.codigo.photo.app.features.home.widgets

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.codigo.photo.data.model.SortOption
import com.deevvdd.sample_rx.R

class SortSpinnerAdapter : ArrayAdapter<SortOption> {

    private var sortOptions = ArrayList<SortOption>()
    private var onSelectSortOptionListener: OnSelectSortOptionListener

    constructor(
        context: Context,
        resource: Int,
        textViewId: Int,
        items: ArrayList<SortOption>,
        onSelectListener: OnSelectSortOptionListener
    ) : super(context, resource, textViewId) {
        this.sortOptions.clear()
        this.sortOptions = items
        this.onSelectSortOptionListener = onSelectListener
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return sortOptions.size
    }

    override fun getItem(position: Int): SortOption? {
        return sortOptions[position]
    }

    class ViewHolder {
        var tvSortOption: TextView? = null
        var ivCheck: ImageView? = null
        var llSortOption: LinearLayout? = null
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view: View?
        val context = parent.context

        if (convertView == null) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.item_sort, null)
            val viewHolder = ViewHolder()
            viewHolder.tvSortOption = view.findViewById<View>(R.id.tvSortOption) as TextView
            viewHolder.ivCheck = view.findViewById<View>(R.id.ivCheck) as ImageView
            viewHolder.llSortOption = view.findViewById<View>(R.id.llSortOption) as LinearLayout
            view.tag = viewHolder
        } else {
            view = convertView
        }
        val holder = view!!.tag as ViewHolder
        holder.tvSortOption?.text = sortOptions[position].name
        if (sortOptions[position].isSelected)
            holder.ivCheck?.visibility = View.VISIBLE
        else
            holder.ivCheck?.visibility = View.GONE
        holder.llSortOption?.setOnClickListener {
            if (sortOptions[position].isSelected) {
                onSelectSortOptionListener.onSelectSortOption(sortOptions[position].name)
            } else {
                for (i in sortOptions.indices) {
                    if (sortOptions[i].isSelected)
                        sortOptions[i].isSelected = false
                }
                sortOptions[position].isSelected = true
                notifyDataSetChanged()
                onSelectSortOptionListener.onSelectSortOption(sortOptions[position].name)
            }
        }
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view: View?
        val context = parent.context

        if (convertView == null) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.item_sort, null)
            val viewHolder = ViewHolder()
            viewHolder.tvSortOption = view.findViewById<View>(R.id.tvSortOption) as TextView
            viewHolder.ivCheck = view.findViewById<View>(R.id.ivCheck) as ImageView
            viewHolder.llSortOption = view.findViewById<View>(R.id.llSortOption) as LinearLayout
            view.tag = viewHolder
        } else {
            view = convertView
        }
        val holder = view!!.tag as ViewHolder
        holder.tvSortOption?.text = sortOptions[position].name
        if (sortOptions[position].isSelected)
            holder.ivCheck?.visibility = View.VISIBLE
        else
            holder.ivCheck?.visibility = View.GONE
        holder.llSortOption?.setOnClickListener {
            if (sortOptions[position].isSelected) {
                onSelectSortOptionListener.onSelectSortOption(sortOptions[position].name)
            } else {
                for (i in sortOptions.indices) {
                    if (sortOptions[i].isSelected)
                        sortOptions[i].isSelected = false
                }
                sortOptions[position].isSelected = true
                notifyDataSetChanged()
                onSelectSortOptionListener.onSelectSortOption(sortOptions[position].name)
            }
        }
        return view
    }
}


interface OnSelectSortOptionListener {
    fun onSelectSortOption(option: String)
}