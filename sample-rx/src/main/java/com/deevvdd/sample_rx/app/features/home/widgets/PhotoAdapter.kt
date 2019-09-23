package com.deevvdd.sample_rx.app.features.home.widgets

import android.app.Activity
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.deevvdd.sample_rx.R
import com.deevvdd.sample_rx.app.utils.load
import com.deevvdd.sample_rx.data.model.response.Hit
import kotlinx.android.synthetic.main.item_photo.view.*

/**
 * Created by heinhtet on 20,September,2019
 */
class PhotoAdapter : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    private var photos = ArrayList<Hit>()


    fun setPhoto(
        photos: List<Hit>,
        page: Int
    ) {
        if(page == 1){
            this.photos.clear()
        }
        this.photos.addAll(photos)
        this.notifyDataSetChanged()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_photo,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return photos.count()
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.onBind(photos[position])
    }

    inner class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val displayMetrics = DisplayMetrics()
        fun onBind(photo: Hit) {
            (itemView.context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
            val height = displayMetrics.heightPixels
            val layoutParam =
                ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height / 2)
            itemView.ivUser.load(photo.userImageURL, true)
            itemView.tvUserName.text = "@${photo.user}"
            itemView.vgPhoto.layoutParams = layoutParam
            itemView.ivPhoto.scaleType = ImageView.ScaleType.CENTER_CROP
            itemView.tvDownloadCount.text = "Download ${photo.downloads}"
            itemView.tvViewCount.text = "View ${photo.views}"
            itemView.ivPhoto.load(photo.webformatURL)

        }

    }
}