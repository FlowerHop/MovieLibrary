package com.flowerhop.movielibrary.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flowerhop.movielibrary.AnyViewModelFactory
import com.flowerhop.movielibrary.R
import com.flowerhop.movielibrary.network.entity.Movie
import com.flowerhop.movielibrary.network.entity.MoviePage
import com.flowerhop.movielibrary.repository.MovieRepository
import com.flowerhop.movielibrary.viewmodel.MoviePageViewModel
import kotlinx.android.synthetic.main.fragment_movie_page.*

/**
 * A fragment representing a list of Items.
 */
class MoviePageFragment : Fragment(R.layout.fragment_movie_page) {
    companion object {
        const val TAG = "MoviePageFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieFactory = AnyViewModelFactory {
            MoviePageViewModel(MovieRepository())
        }

        val moviePageViewModel: MoviePageViewModel = ViewModelProvider(this, movieFactory).get(MoviePageViewModel::class.java)
        val moviePageRecyclerViewAdapter = MoviePageRecyclerViewAdapter()

        moviePageViewModel.pageList.observe(viewLifecycleOwner) { newPageList ->
            val movies = newPageList.flatMap { it.movies }
            moviePageRecyclerViewAdapter.submitList(movies)
        }

        movieList.apply {
            val layoutMgr = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            layoutManager = layoutMgr
            adapter = moviePageRecyclerViewAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState != RecyclerView.SCROLL_STATE_IDLE) return
                    val currentItemPosition = layoutMgr.findLastVisibleItemPosition()
                    if (currentItemPosition != moviePageRecyclerViewAdapter.itemCount - 1) return
                    moviePageViewModel.loadNewPage()
                }
            })
        }
    }
}