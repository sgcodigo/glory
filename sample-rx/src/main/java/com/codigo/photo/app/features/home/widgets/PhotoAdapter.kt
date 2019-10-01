package com.codigo.photo.app.features.home.widgets

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codigo.photo.app.features.details.PhotoDetailsActivity
import com.codigo.photo.app.utils.load
import com.codigo.photo.data.model.response.Hit
import com.deevvdd.sample_rx.R
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
        if (page == 1) {
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
        fun onBind(photo: Hit) {
            itemView.ivUser.load(photo.userImageURL, true)
            itemView.tvUserName.text = photo.user
            itemView.tvDownloadCount.text = "${photo.downloads}"
            itemView.tvViewCount.text = "${photo.views}"
            itemView.ivPhoto.load(photo.webformatURL)
            itemView.setOnClickListener { PhotoDetailsActivity.startMe(itemView.context, photo) }
        }
    }
}
