package com.flowerhop.movielibrary.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.flowerhop.movielibrary.CategoryActivity
import com.flowerhop.movielibrary.R
import com.flowerhop.movielibrary.view.BundleKey.CATEGORY
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
            shimmerLists.visibility = if (it) View.VISIBLE else View.GONE
            nowPlayingList.visibility = if (it) View.GONE else View.VISIBLE
            popularList.visibility = if (it) View.GONE else View.VISIBLE
            topRatedList.visibility = if (it) View.GONE else View.VISIBLE
        })

        moviesViewModel.refresh()
        initNowPlaying()
        initPopular()
        initTopRated()
    }

    private fun initNowPlaying() {
        val nowPlayingAdapter = MoviesAdapter()
        moviesViewModel.nowPlayingList.observe(viewLifecycleOwner) {
            nowPlayingAdapter.submitList(it.data.toMutableList())
        }

        nowPlayingList.title.text = resources.getText(R.string.now_playing)
        nowPlayingList.list.adapter = nowPlayingAdapter
        nowPlayingList.list.isNestedScrollingEnabled = false
        nowPlayingList.onEventListener = object : SimpleCategoryList.OnEventListener {
            override fun onViewAll() {
                toViewAll(MovieCategory.NowPlaying)
            }
        }
    }

    private fun initPopular() {
        val popularAdapter = MoviesAdapter()
        moviesViewModel.popularList.observe(viewLifecycleOwner) {
            popularAdapter.submitList(it.data.toMutableList())
        }

        popularList.title.text = resources.getText(R.string.popular)
        popularList.list.adapter = popularAdapter
        popularList.list.isNestedScrollingEnabled = false
        popularList.onEventListener = object : SimpleCategoryList.OnEventListener {
            override fun onViewAll() {
                toViewAll(MovieCategory.Popular)
            }
        }
    }

    private fun initTopRated() {
        val topRatedAdapter = MoviesAdapter()
        moviesViewModel.topRatedList.observe(viewLifecycleOwner) {
            topRatedAdapter.submitList(it.data.toMutableList())
        }

        topRatedList.title.text = resources.getText(R.string.top_rated)
        topRatedList.list.adapter = topRatedAdapter
        topRatedList.list.isNestedScrollingEnabled = false
        topRatedList.onEventListener = object : SimpleCategoryList.OnEventListener {
            override fun onViewAll() {
                toViewAll(MovieCategory.TopRated)
            }
        }
    }

    private fun toViewAll(category: MovieCategory) {
        requireActivity().startActivity(
            Intent().apply {
                setClass(requireContext(), CategoryActivity::class.java)
                putExtra(CATEGORY, category.ordinal)
            }
        )
    }
}