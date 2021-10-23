package com.flowerhop.movielibrary.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.flowerhop.movielibrary.R
import com.flowerhop.movielibrary.di.Providers
import com.flowerhop.movielibrary.presentation.MoviesAdapter
import com.flowerhop.movielibrary.presentation.MoviesViewModel
import kotlinx.android.synthetic.main.fragment_home.*

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

        nowPlayingMore.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().apply {
                add(R.id.fragmentContainer, MoviePageFragment::class.java, null, TAG)
                addToBackStack(null)
                commit()
            }
        }
    }

    private fun initNowPlaying() {
        val nowPlayingAdapter = MoviesAdapter()
        moviesViewModel.nowPlayingList.observe(viewLifecycleOwner) {
            nowPlayingTitle.visibility = if (it.isLoading) View.GONE else View.VISIBLE
            nowPlayingAdapter.submitList(it.data.toMutableList())
        }

        nowPlayingList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        nowPlayingList.adapter = nowPlayingAdapter
    }

    private fun initPopular() {
        val popularAdapter = MoviesAdapter()
        moviesViewModel.popularList.observe(viewLifecycleOwner) {
            popularTitle.visibility = if (it.isLoading) View.GONE else View.VISIBLE
            popularAdapter.submitList(it.data.toMutableList())
        }

        popularList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        popularList.adapter = popularAdapter
    }

    private fun initTopRated() {
        val topRatedAdapter = MoviesAdapter()
        moviesViewModel.topRatedList.observe(viewLifecycleOwner) {
            topRatedTitle.visibility = if (it.isLoading) View.GONE else View.VISIBLE
            topRatedAdapter.submitList(it.data.toMutableList())
        }

        topRatedList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        topRatedList.adapter = topRatedAdapter
    }
}