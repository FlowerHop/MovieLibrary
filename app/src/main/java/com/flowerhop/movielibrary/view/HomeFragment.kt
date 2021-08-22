package com.flowerhop.movielibrary.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.flowerhop.movielibrary.AnyViewModelFactory
import com.flowerhop.movielibrary.R
import com.flowerhop.movielibrary.repository.MovieRepository
import com.flowerhop.movielibrary.viewmodel.MoviesViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment: Fragment(R.layout.fragment_home) {
    companion object {
        const val TAG = "HomeFragment"
    }

    private lateinit var moviesViewModel: MoviesViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieViewModelFactory = AnyViewModelFactory{
            MoviesViewModel(MovieRepository())
        }

        moviesViewModel = ViewModelProvider(this, movieViewModelFactory).get(MoviesViewModel::class.java)
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

        val movieList = moviesViewModel.getList(MovieCategory.NowPlaying)
        movieList.refreshing.observe(viewLifecycleOwner, {
            nowPlayingTitle.visibility = if (it) View.GONE else View.VISIBLE
        })
        movieList.movies.observe(viewLifecycleOwner, {
            nowPlayingAdapter.submit(it)
        })

        nowPlayingList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        nowPlayingList.adapter = nowPlayingAdapter
    }

    private fun initPopular() {
        val popularAdapter = MoviesAdapter()

        val movieList = moviesViewModel.getList(MovieCategory.Popular)
        movieList.refreshing.observe(viewLifecycleOwner, {
            popularTitle.visibility = if (it) View.GONE else View.VISIBLE
        })
        movieList.movies.observe(viewLifecycleOwner, {
            popularAdapter.submit(it)
        })

        popularList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        popularList.adapter = popularAdapter
    }

    private fun initTopRated() {
        val topRatedAdapter = MoviesAdapter()

        val movieList = moviesViewModel.getList(MovieCategory.TopRated)
        movieList.refreshing.observe(viewLifecycleOwner, {
            topRatedTitle.visibility = if (it) View.GONE else View.VISIBLE
        })
        movieList.movies.observe(viewLifecycleOwner, {
            topRatedAdapter.submit(it)
        })

        topRatedList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        topRatedList.adapter = topRatedAdapter
    }
}