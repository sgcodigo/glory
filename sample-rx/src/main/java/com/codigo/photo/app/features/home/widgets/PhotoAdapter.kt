package com.codigo.photo.app.features.home.widgets

import android.app.Activity
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.codigo.photo.app.utils.EMPTY
import com.codigo.photo.app.utils.LOADING
import com.codigo.photo.app.utils.load
import com.codigo.photo.data.model.response.Hit
import com.deevvdd.sample_rx.R
import kotlinx.android.synthetic.main.item_loading.view.*
import kotlinx.android.synthetic.main.item_photo.view.*

/**
 * Created by heinhtet on 20,September,2019
 */
class PhotoAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_LOADING = 0
    private val VIEW_EMPTY = 1
    private val VIEW_PHOTO = 2
    private var photos = ArrayList<Hit>()

    fun setPhoto(
        photoList: List<Hit>,
        page: Int
    ) {
        if (page == 1) {
            photos.clear()
        } else {
            if (photos.isNotEmpty() && (photos[photos.size - 1].id == -1)) {
                photos.removeAt(photos.size - 1)
                notifyItemRemoved(photos.size)
            }
        }
        photos.addAll(photoList)
        this.notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when (photos[position].type) {
            LOADING -> VIEW_LOADING
            EMPTY -> VIEW_EMPTY
            else -> VIEW_PHOTO
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            VIEW_LOADING -> {
                return LoadingViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_loading,
                        parent,
                        false
                    )
                )
            }
            VIEW_EMPTY -> {
                return LoadingViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_loading,
                        parent,
                        false
                    )
                )
            }
            VIEW_PHOTO -> {
                return PhotoViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_photo,
                        parent,
                        false
                    )
                )
            }
            else -> throw RuntimeException("Unknown View")
        }
    }

    override fun getItemCount(): Int {
        return photos.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PhotoViewHolder -> {
                holder.onBind(photos[position])
            }

            is LoadingViewHolder -> {
                holder.onBind(photos[position])
            }
        }
    }

    inner class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val displayMetrics = DisplayMetrics()
        fun onBind(photo: Hit) {
            (itemView.context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
            val height = displayMetrics.heightPixels
            val layoutParam =
                FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, height / 2)
            val marginInDp = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 8F, displayMetrics
            ).toInt()
            layoutParam.setMargins(marginInDp, marginInDp, marginInDp, marginInDp)
            itemView.ivUser.load(photo.userImageURL, true)
            itemView.tvUserName.text = "@${photo.user}"
            itemView.vgPhoto.layoutParams = layoutParam
            itemView.ivPhoto.scaleType = ImageView.ScaleType.CENTER_CROP
            itemView.tvDownloadCount.text = "Download ${photo.downloads}"
            itemView.tvViewCount.text = "View ${photo.views}"
            itemView.ivPhoto.load(photo.webformatURL)
        }
    }

    inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(photo: Hit) {
            if (photo.type == LOADING) {
                itemView.pgLoading.visibility = View.VISIBLE
                itemView.tvEmpty.visibility = View.GONE
            } else if (photo.type == EMPTY) {
                itemView.pgLoading.visibility = View.GONE
                itemView.tvEmpty.visibility = View.VISIBLE
            }
        }
    }
}

