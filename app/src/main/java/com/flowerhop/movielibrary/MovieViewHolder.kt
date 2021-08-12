package com.flowerhop.movielibrary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flowerhop.movielibrary.network.APIClient
import com.flowerhop.movielibrary.network.entity.Movie
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
) {
    fun bind(movie: Movie) {
        itemView.title.text = movie.title
        itemView.overview.text = movie.overview
        Glide.with(this.itemView).load("${APIClient.IMAGE_BASE_URL}${movie.posterPath}").into(itemView.thumbnail)
        itemView.setOnClickListener {
            MovieBottomSheetDialog.show((itemView.context as FragmentActivity).supportFragmentManager, movie)
        }
    }
}