package com.flowerhop.movielibrary.presentation

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.flowerhop.movielibrary.comman.Constants
import com.flowerhop.movielibrary.databinding.MovieItemInPageBinding
import com.flowerhop.movielibrary.domain.model.Movie
import com.flowerhop.movielibrary.view.MovieInfoView
import com.google.android.material.imageview.ShapeableImageView

class MovieCategoryAdapter(
    var onClickListener: ((Int) -> Unit)? = null,
    var onFavoriteListener: ((id: Int, add: Boolean) -> Unit)? = null
) : ListAdapter<Movie, MovieCategoryAdapter.ViewHolder>(object : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: Movie, newItem: Movie): Movie {
        return newItem
    }
}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MovieItemInPageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding = binding).apply {
            binding.root.setOnClickListener { onClickListener?.invoke(absoluteAdapterPosition) }
            binding.movieInfoView.onFavoriteListener = {
                val item = getItem(absoluteAdapterPosition)
                onFavoriteListener?.invoke(
                    item.id,
                    !item.myFavorite
                )
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindImage(getItem(position))
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            payloads.forEach {
                (it as? Movie)?.let { movie ->
                    holder.bind(movie)
                }
            }
        }
    }

    class ViewHolder(binding: MovieItemInPageBinding) : RecyclerView.ViewHolder(binding.root) {
        private val thumbnailView: ShapeableImageView = binding.thumbnail
        private val movieInfoView: MovieInfoView = binding.movieInfoView

        fun bindImage(movie: Movie) {
            Glide.with(thumbnailView).load("${Constants.IMAGE_BASE_URL}${movie.posterPath}")
                .into(thumbnailView)
        }

        fun bind(movie: Movie) {
            movieInfoView.setMovie(movie)
        }
    }
}