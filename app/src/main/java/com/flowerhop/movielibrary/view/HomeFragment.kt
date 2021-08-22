package com.flowerhop.movielibrary.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
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
    private val isNowPlayingRefreshingLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
    private val isPopularRefreshingLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
    private val isTopRatedRefreshingLiveData: MutableLiveData<Boolean> = MutableLiveData(false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieViewModelFactory = AnyViewModelFactory{
            MoviesViewModel(MovieRepository())
        }

        moviesViewModel = ViewModelProvider(this, movieViewModelFactory).get(MoviesViewModel::class.java)

        refreshLayout.setOnRefreshListener {
            nowPlayingTitle.visibility = View.GONE
            popularTitle.visibility = View.GONE
            topRatedTitle.visibility = View.GONE

            isNowPlayingRefreshingLiveData.postValue(true)
            isPopularRefreshingLiveData.postValue(true)
            isTopRatedRefreshingLiveData.postValue(true)
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
        isNowPlayingRefreshingLiveData.apply {
            observe(viewLifecycleOwner, {
                nowPlayingTitle.visibility = if (it) View.GONE else View.VISIBLE
                refreshLayout.isRefreshing = it || isPopularRefreshingLiveData.value!! || isTopRatedRefreshingLiveData.value!!
            })
        }

        moviesViewModel.getList(MovieCategory.NowPlaying).observe(viewLifecycleOwner, {
            nowPlayingAdapter.submit(it)
            nowPlayingTitle.visibility = View.VISIBLE
            isNowPlayingRefreshingLiveData.postValue(false)
        })

        nowPlayingList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        nowPlayingList.adapter = nowPlayingAdapter
    }

    private fun initPopular() {
        val popularAdapter = MoviesAdapter()

        isPopularRefreshingLiveData.apply {
            observe(viewLifecycleOwner, {
                popularTitle.visibility = if (it) View.GONE else View.VISIBLE
                refreshLayout.isRefreshing = it || isNowPlayingRefreshingLiveData.value!! || isTopRatedRefreshingLiveData.value!!
            })
        }

        moviesViewModel.getList(MovieCategory.Popular).observe(viewLifecycleOwner, {
            popularAdapter.submit(it)
            popularTitle.visibility = View.VISIBLE
            isPopularRefreshingLiveData.postValue(false)
        })

        popularList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        popularList.adapter = popularAdapter
    }

    private fun initTopRated() {
        val topRatedAdapter = MoviesAdapter()

        isTopRatedRefreshingLiveData.apply {
            observe(viewLifecycleOwner, {
                topRatedTitle.visibility = if (it) View.GONE else View.VISIBLE
                refreshLayout.isRefreshing = it || isNowPlayingRefreshingLiveData.value!! || isPopularRefreshingLiveData.value!!
            })
        }

        moviesViewModel.getList(MovieCategory.TopRated).observe(viewLifecycleOwner, {
            topRatedAdapter.submit(it)
            topRatedTitle.visibility = View.VISIBLE
            isTopRatedRefreshingLiveData.postValue(false)
        })

        topRatedList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        topRatedList.adapter = topRatedAdapter
    }
}