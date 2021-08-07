package com.flowerhop.movielibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flowerhop.movielibrary.databinding.ActivityMainBinding
import com.flowerhop.movielibrary.network.APIClient
import com.flowerhop.movielibrary.network.entity.Movie
import com.flowerhop.movielibrary.network.entity.MoviePage
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_movie.view.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieViewModelFactory = AnyViewModelFactory{
            MoviesViewModel(MovieRepository())
        }

        val adapter = MoviesAdapter()
        val nowPlayingViewModel = ViewModelProvider(this, movieViewModelFactory).get(MoviesViewModel::class.java)
        nowPlayingViewModel.nowPlayings.observe(this, {
            adapter.submit(it)
        })

        nowPlayingList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        nowPlayingList.adapter = adapter
    }
}