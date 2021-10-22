package com.flowerhop.movielibrary.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flowerhop.movielibrary.AnyViewModelFactory
import com.flowerhop.movielibrary.R
import com.flowerhop.movielibrary.data.remote.TMDBApi
import com.flowerhop.movielibrary.data.repository.TMDBRepositoryImpl
import com.flowerhop.movielibrary.domain.repository.TMDBRepository
import com.flowerhop.movielibrary.domain.usecase.GetCategoryListUseCase
import com.flowerhop.movielibrary.network.APIClient
import com.flowerhop.movielibrary.presentation.MoviePageRecyclerViewAdapter
import com.flowerhop.movielibrary.presentation.categorylist.MovieCategoryViewModel
import kotlinx.android.synthetic.main.fragment_movie_page.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A fragment representing a list of Items.
 */
class MoviePageFragment : Fragment(R.layout.fragment_movie_page) {
    companion object {
        const val TAG = "MoviePageFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieCategoryFactory = AnyViewModelFactory {
//            MoviePageViewModel(MovieRepository())
            val okHttp = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build()
            val retrofit = Retrofit.Builder()
                .baseUrl(APIClient.BASE_URL)
                .client(okHttp)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val tmdbApi = retrofit.create(TMDBApi::class.java)

            val tmdbRepository: TMDBRepository = TMDBRepositoryImpl(tmdbApi)
            MovieCategoryViewModel(
                MovieCategory.NowPlaying,
                GetCategoryListUseCase(tmdbRepository)
            )
        }

        val movieCategoryViewModel: MovieCategoryViewModel = ViewModelProvider(this, movieCategoryFactory).get(MovieCategoryViewModel::class.java)
        val moviePageRecyclerViewAdapter = MoviePageRecyclerViewAdapter()

        movieCategoryViewModel.movies.observe(viewLifecycleOwner) { movies ->
            Log.e(TAG, "onViewCreated: $movies" )
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