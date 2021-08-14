package com.flowerhop.movielibrary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.flowerhop.movielibrary.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment: Fragment() {
    companion object {
        const val TAG = "HomeFragment"
    }
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieViewModelFactory = AnyViewModelFactory{
            MoviesViewModel(MovieRepository())
        }

        val nowPlayingAdapter = MoviesAdapter()
        val popularAdapter = MoviesAdapter()
        val topRatedAdapter = MoviesAdapter()
        val movieViewModel = ViewModelProvider(this, movieViewModelFactory).get(MoviesViewModel::class.java)
        val refreshLayout = binding.refreshLayout

        val isNowPlayingRefreshingLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
        val isPopularRefreshingLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
        val isTopRatedRefreshingLiveData: MutableLiveData<Boolean> = MutableLiveData(false)

        isNowPlayingRefreshingLiveData.apply {
            observe(viewLifecycleOwner, {
                nowPlayingTitle.visibility = if (it) View.GONE else View.VISIBLE
                refreshLayout.isRefreshing = it || isPopularRefreshingLiveData.value!! || isTopRatedRefreshingLiveData.value!!
            })
        }
        isPopularRefreshingLiveData.apply {
            observe(viewLifecycleOwner, {
                popularTitle.visibility = if (it) View.GONE else View.VISIBLE
                refreshLayout.isRefreshing = it || isNowPlayingRefreshingLiveData.value!! || isTopRatedRefreshingLiveData.value!!
            })
        }
        isTopRatedRefreshingLiveData.apply {
            observe(viewLifecycleOwner, {
                topRatedTitle.visibility = if (it) View.GONE else View.VISIBLE
                refreshLayout.isRefreshing = it || isNowPlayingRefreshingLiveData.value!! || isPopularRefreshingLiveData.value!!
            })
        }

        refreshLayout.setOnRefreshListener {
            nowPlayingTitle.visibility = View.GONE
            popularTitle.visibility = View.GONE
            topRatedTitle.visibility = View.GONE

            isNowPlayingRefreshingLiveData.postValue(true)
            isPopularRefreshingLiveData.postValue(true)
            isTopRatedRefreshingLiveData.postValue(true)
            movieViewModel.refresh()
        }
        movieViewModel.nowPlayings.observe(viewLifecycleOwner, {
            nowPlayingAdapter.submit(it)
            nowPlayingTitle.visibility = View.VISIBLE
            isNowPlayingRefreshingLiveData.postValue(false)
        })
        movieViewModel.populars.observe(viewLifecycleOwner, {
            popularAdapter.submit(it)
            popularTitle.visibility = View.VISIBLE
            isPopularRefreshingLiveData.postValue(false)
        })
        movieViewModel.topRatedList.observe(viewLifecycleOwner, {
            topRatedAdapter.submit(it)
            topRatedTitle.visibility = View.VISIBLE
            isTopRatedRefreshingLiveData.postValue(false)
        })

        nowPlayingList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        nowPlayingList.adapter = nowPlayingAdapter

        popularList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        popularList.adapter = popularAdapter

        topRatedList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        topRatedList.adapter = topRatedAdapter
    }
}