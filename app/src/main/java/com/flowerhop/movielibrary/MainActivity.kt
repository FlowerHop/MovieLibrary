package com.flowerhop.movielibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.flowerhop.movielibrary.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
            observe(this@MainActivity, {
                nowPlayingTitle.visibility = if (it) View.GONE else View.VISIBLE
                refreshLayout.isRefreshing = it || isPopularRefreshingLiveData.value!! || isTopRatedRefreshingLiveData.value!!
            })
        }
        isPopularRefreshingLiveData.apply {
            observe(this@MainActivity, {
                popularTitle.visibility = if (it) View.GONE else View.VISIBLE
                refreshLayout.isRefreshing = it || isNowPlayingRefreshingLiveData.value!! || isTopRatedRefreshingLiveData.value!!
            })
        }
        isTopRatedRefreshingLiveData.apply {
            observe(this@MainActivity, {
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
        movieViewModel.nowPlayings.observe(this, {
            nowPlayingAdapter.submit(it)
            nowPlayingTitle.visibility = View.VISIBLE
            isNowPlayingRefreshingLiveData.postValue(false)
        })
        movieViewModel.populars.observe(this, {
            popularAdapter.submit(it)
            popularTitle.visibility = View.VISIBLE
            isPopularRefreshingLiveData.postValue(false)
        })
        movieViewModel.topRatedList.observe(this, {
            topRatedAdapter.submit(it)
            topRatedTitle.visibility = View.VISIBLE
            isTopRatedRefreshingLiveData.postValue(false)
        })

        nowPlayingList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        nowPlayingList.adapter = nowPlayingAdapter

        popularList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        popularList.adapter = popularAdapter

        topRatedList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        topRatedList.adapter = topRatedAdapter
    }
}