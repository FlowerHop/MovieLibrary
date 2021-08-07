package com.flowerhop.movielibrary

import android.util.Log
import com.flowerhop.movielibrary.network.APIClient
import com.flowerhop.movielibrary.network.entity.Movie
import com.flowerhop.movielibrary.network.entity.MovieDetail
import com.flowerhop.movielibrary.network.entity.MoviePage

class MovieRepository {
    private val apiClient = APIClient()

    suspend fun getMovie(id: Int): MovieDetail {
        return apiClient.getMovie(id)
    }

    suspend fun getNowPlaying(pageIndex: Int): MoviePage {
        return apiClient.getNowPlaying(pageIndex)
    }

    suspend fun getPopular(pageIndex: Int = 1): MoviePage {
        return apiClient.getPopular(pageIndex)
    }

    suspend fun getTopRated(pageIndex: Int = 1): MoviePage {
        return apiClient.getTopRated(pageIndex)
    }
}