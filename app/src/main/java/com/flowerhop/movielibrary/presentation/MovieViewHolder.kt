package com.flowerhop.movielibrary.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flowerhop.movielibrary.R
import com.flowerhop.movielibrary.comman.Constants
import com.flowerhop.movielibrary.domain.model.Movie
import com.flowerhop.movielibrary.view.MovieBottomSheetDialog
import kotlinx.android.synthetic.main.item_movie.view.thumbnail

class MovieViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_movie_rounded, parent, false)
) {
    fun bind(movie: Movie) {
//        itemView.title.text = movie.title
//        itemView.overview.text = movie.overview
        Glide.with(this.itemView).load("${Constants.IMAGE_BASE_URL}${movie.posterPath}").into(itemView.thumbnail)
        itemView.setOnClickListener {
            MovieBottomSheetDialog.show(
                (itemView.context as FragmentActivity).supportFragmentManager,
                movie
            )
        }
    }
}