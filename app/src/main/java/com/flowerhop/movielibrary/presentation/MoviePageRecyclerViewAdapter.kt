package com.flowerhop.movielibrary.presentation

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.flowerhop.movielibrary.databinding.MovieItemInPageBinding
import com.flowerhop.movielibrary.domain.model.Movie
import com.flowerhop.movielibrary.network.APIClient

class MoviePageRecyclerViewAdapter: ListAdapter<Movie, MoviePageRecyclerViewAdapter.ViewHolder>(object : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(MovieItemInPageBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.getItem(position))
    }

    inner class ViewHolder(binding: MovieItemInPageBinding) : RecyclerView.ViewHolder(binding.root) {
        private val thumbnailView: AppCompatImageView = binding.thumbnail
        private val titleView: AppCompatTextView = binding.title
        private val releaseDateView: AppCompatTextView = binding.releaseDate
        private val overviewView: AppCompatTextView = binding.overview

        fun bind(movie: Movie) {
            Glide.with(thumbnailView).load("${APIClient.IMAGE_BASE_URL}${movie.posterPath}").into(thumbnailView)
            titleView.text = movie.title
            releaseDateView.text = movie.releaseDate
            overviewView.text = movie.overview
        }
    }
}