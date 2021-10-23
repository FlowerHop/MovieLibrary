package com.flowerhop.movielibrary.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flowerhop.movielibrary.R
import com.flowerhop.movielibrary.di.Providers
import com.flowerhop.movielibrary.presentation.MovieCategoryAdapter
import com.flowerhop.movielibrary.presentation.categorylist.MovieCategoryViewModel
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
        val movieCategoryViewModel: MovieCategoryViewModel = Providers.provideMovieCategoryViewModel(this, MovieCategory.NowPlaying)
        val moviePageRecyclerViewAdapter = MovieCategoryAdapter()

        movieCategoryViewModel.movies.observe(viewLifecycleOwner) { movies ->
            moviePageRecyclerViewAdapter.submitList(movies.toMutableList())
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
                    movieCategoryViewModel.loadMoreIfNeed(currentItemPosition)
                }
            })
        }
    }
}