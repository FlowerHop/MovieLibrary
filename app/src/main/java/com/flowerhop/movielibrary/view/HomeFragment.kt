package com.flowerhop.movielibrary.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.flowerhop.movielibrary.R
import com.flowerhop.movielibrary.di.Providers
import com.flowerhop.movielibrary.domain.model.MovieCategory
import com.flowerhop.movielibrary.presentation.MoviesAdapter
import com.flowerhop.movielibrary.presentation.MoviesViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.simple_category_list.view.*

class HomeFragment: Fragment(R.layout.fragment_home) {
    companion object {
        const val TAG = "HomeFragment"
    }

    private lateinit var moviesViewModel: MoviesViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moviesViewModel = Providers.provideMoviesViewModel(this)
        moviesViewModel.refreshing.observe(viewLifecycleOwner, {
            refreshLayout.isRefreshing = it
        })

        refreshLayout.setOnRefreshListener {
            moviesViewModel.refresh()
        }

        moviesViewModel.refresh()
        initNowPlaying()
        initPopular()
        initTopRated()
    }

    private fun initNowPlaying() {
        val nowPlayingAdapter = MoviesAdapter()
        moviesViewModel.nowPlayingList.observe(viewLifecycleOwner) {
            nowPlayingList.title.visibility = if (it.isLoading) View.GONE else View.VISIBLE
            nowPlayingAdapter.submitList(it.data.toMutableList())
        }

        nowPlayingList.title.text = "Now Playing"
        nowPlayingList.list.adapter = nowPlayingAdapter
        nowPlayingList.onEventListener = object : SimpleCategoryList.OnEventListener {
            override fun onViewAll() {
                toViewAll(MovieCategory.NowPlaying)
            }
        }
    }

    private fun initPopular() {
        val popularAdapter = MoviesAdapter()
        moviesViewModel.popularList.observe(viewLifecycleOwner) {
            popularList.title.visibility = if (it.isLoading) View.GONE else View.VISIBLE
            popularAdapter.submitList(it.data.toMutableList())
        }

        popularList.title.text = "Popular"
        popularList.list.adapter = popularAdapter
        popularList.onEventListener = object : SimpleCategoryList.OnEventListener {
            override fun onViewAll() {
                toViewAll(MovieCategory.Popular)
            }
        }
    }

    private fun initTopRated() {
        val topRatedAdapter = MoviesAdapter()
        moviesViewModel.topRatedList.observe(viewLifecycleOwner) {
            topRatedList.title.visibility = if (it.isLoading) View.GONE else View.VISIBLE
            topRatedAdapter.submitList(it.data.toMutableList())
        }

        topRatedList.title.text = "Top Rated"
        topRatedList.list.adapter = topRatedAdapter
        topRatedList.onEventListener = object : SimpleCategoryList.OnEventListener {
            override fun onViewAll() {
                toViewAll(MovieCategory.TopRated)
            }
        }
    }

    private fun toViewAll(category: MovieCategory) {
        // TODO: show fragment for the category
        requireActivity().supportFragmentManager.beginTransaction().apply {
            add(R.id.fragmentContainer, MoviePageFragment::class.java, null, TAG)
            addToBackStack(null)
            commit()
        }
    }
}