package com.flowerhop.movielibrary.view

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.flowerhop.movielibrary.network.entity.Movie

class MoviesAdapter(): RecyclerView.Adapter<MovieViewHolder>() {
    private var movies: List<Movie> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(parent)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size
    fun submit(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }
}