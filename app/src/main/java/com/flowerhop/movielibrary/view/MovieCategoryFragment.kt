package com.flowerhop.movielibrary.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flowerhop.movielibrary.R
import com.flowerhop.movielibrary.di.Providers
import com.flowerhop.movielibrary.domain.model.MovieCategory
import com.flowerhop.movielibrary.presentation.MovieCategoryAdapter
import com.flowerhop.movielibrary.presentation.categorylist.MovieCategoryViewModel
import kotlinx.android.synthetic.main.fragment_movie_page.*

/**
 * A fragment representing a list of Items.
 */
class MovieCategoryFragment : Fragment(R.layout.fragment_movie_page) {
    companion object {
        const val TAG = "MovieCategoryFragment"
        const val KEY_CATEGORY = "KEY_CATEGORY"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieCategoryViewModel: MovieCategoryViewModel = arguments?.run {
            val ordinal = getInt(KEY_CATEGORY)
            val category = MovieCategory.values()[ordinal]
            Providers.provideMovieCategoryViewModel(this@MovieCategoryFragment, category)
        } ?: Providers.provideMovieCategoryViewModel(this, MovieCategory.NowPlaying)

        val moviePageRecyclerViewAdapter = MovieCategoryAdapter {
            val movieId = movieCategoryViewModel.movies.value?.get(it)?.id ?: return@MovieCategoryAdapter

            requireActivity().supportFragmentManager.beginTransaction().apply {
                add(R.id.fragmentContainer, MovieDetailFragment::class.java,
                    bundleOf(MovieDetailFragment.KEY_ID to movieId),
                    MovieDetailFragment.TAG)
                addToBackStack(null)
                commit()
            }
        }

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