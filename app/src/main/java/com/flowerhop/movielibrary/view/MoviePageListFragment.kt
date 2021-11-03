package com.flowerhop.movielibrary.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flowerhop.movielibrary.R
import com.flowerhop.movielibrary.view.BundleKey.CATEGORY
import com.flowerhop.movielibrary.view.BundleKey.GENRE_ID
import com.flowerhop.movielibrary.view.BundleKey.GENRE_NAME
import com.flowerhop.movielibrary.view.BundleKey.MOVIE_ID
import com.flowerhop.movielibrary.data.dto.Genre
import com.flowerhop.movielibrary.di.Providers
import com.flowerhop.movielibrary.domain.model.MovieCategory
import com.flowerhop.movielibrary.presentation.MovieCategoryAdapter
import kotlinx.android.synthetic.main.fragment_movie_page.*
import kotlin.math.log

/**
 * A fragment representing a list of Items.
 */
class MoviePageListFragment : Fragment(R.layout.fragment_movie_page) {
    companion object {
        const val TAG = "MoviePageListFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.run {
            if (containsKey(CATEGORY)) {
                val ordinal = getInt(CATEGORY)
                val category = MovieCategory.values()[ordinal]
                initWithCategory(category)
            } else if (containsKey((GENRE_ID))) {
                val genreName = getString(GENRE_NAME) ?: ""
                val genreId = getInt(GENRE_ID)
                initWithGenre(Genre(genreId, genreName))
            }
        }
    }


    private fun initWithCategory(movieCategory: MovieCategory) {
        val movieCategoryViewModel = Providers.provideMovieCategoryViewModel(this@MoviePageListFragment, movieCategory)
        setupToolBar(movieCategory.name)

        val moviePageRecyclerViewAdapter = MovieCategoryAdapter {
            val movieId = movieCategoryViewModel.movies.value?.get(it)?.id ?: return@MovieCategoryAdapter

            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainer, MovieDetailFragment::class.java,
                    bundleOf(MOVIE_ID to movieId),
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

    override fun onResume() {
        super.onResume()
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.let {
            it.show()
        }
    }

    private fun initWithGenre(genre: Genre) {
        val movieGenreViewModel = Providers.provideMovieGenreViewModel(this@MoviePageListFragment, genre)
        setupToolBar(genre.name)

        val moviePageRecyclerViewAdapter = MovieCategoryAdapter {
            val movieId = movieGenreViewModel.movies.value?.get(it)?.id ?: return@MovieCategoryAdapter

            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainer, MovieDetailFragment::class.java,
                    bundleOf(MOVIE_ID to movieId),
                    MovieDetailFragment.TAG)
                addToBackStack(null)
                commit()
            }
        }

        movieGenreViewModel.movies.observe(viewLifecycleOwner) { movies ->
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
                    movieGenreViewModel.loadMoreIfNeed(currentItemPosition)
                }
            })
        }
    }

    private fun setupToolBar(title: String) {
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.apply {
            show()
            this.title = title
            setDisplayShowTitleEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    override fun onDestroyView() {
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.hide()
        super.onDestroyView()
    }
}