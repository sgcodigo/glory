package com.codigo.photo.app.features.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.codigo.photo.app.utils.load
import com.codigo.photo.data.model.response.Hit
import com.deevvdd.sample_rx.R
import kotlinx.android.synthetic.main.activity_photo_details.*
import kotlinx.android.synthetic.main.layout_photo_info.*


/**
 * Created by johnsonmaung on 1,October,2019
 */
class PhotoDetailsActivity : AppCompatActivity() {

    var photo: Hit? = null

    companion object {
        fun startMe(context: Context, hit: Hit) {
            val intent = Intent(context, PhotoDetailsActivity::class.java)
            intent.putExtra("hit", hit)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_details)
        ivBack.setOnClickListener { finish() }
        photo = intent.getSerializableExtra("hit") as Hit?
        photo.let {
            iv.load(photo?.largeImageURL)
            ivUser.load(photo?.userImageURL, true)
            tvUserName.text = photo?.user
            tvViewCount.text = "${photo?.views} views"
            tvLikesCount.text = "${photo?.likes} likes"
            tvDownloadCount.text = "${photo?.downloads} downloads"
            tvCommentsCount.text = "${photo?.comments} comments"
            tvSize.text = "${photo?.imageWidth} X ${photo?.imageHeight}"
            tvTagsCount.text = "${photo?.tags?.split(", ")?.size ?: 0} tags"
            tagGroup.setTags(photo?.tags?.split(", ") ?: emptyList())
        }

    }

}
