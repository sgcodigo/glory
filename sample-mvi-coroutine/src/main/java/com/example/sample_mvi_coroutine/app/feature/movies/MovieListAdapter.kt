package com.example.sample_mvi_coroutine.app.feature.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.BlurTransformation
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.example.sample_mvi_coroutine.R
import com.example.sample_mvi_coroutine.data.util.IMAGE_BASE_URL
import com.example.sample_mvi_coroutine.domain.model.Movie
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieListAdapter: ListAdapter<Movie, MovieListAdapter.MovieViewHolder>(MovieDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
            .let { MovieViewHolder(it) }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MovieViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val tvName by lazy { itemView.tvName }
        private val ivPoster by lazy { itemView.ivPoster }

        fun bind(data: Movie) {
            tvName.text = data.title
            ivPoster.load("$IMAGE_BASE_URL${data.posterPath}") {
                crossfade(300)
                transformations(RoundedCornersTransformation(16f))
            }
        }
    }
}

object MovieDiffCallback: DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem

}