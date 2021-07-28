package com.flowerhop.movielibrary.network

import com.flowerhop.movielibrary.network.entity.MovieDetail
import com.flowerhop.movielibrary.network.entity.MoviePage
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIClient() {
    companion object {
        const val TAG = "APIClient"
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "a56648c829ad25080106ba1c138c3e0b"
    }

    private val okHttp: Call.Factory
    private val retrofit: Retrofit
    private val movieAPI: MovieApi

    init {
        okHttp = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }).build()
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        movieAPI = retrofit.create(MovieApi::class.java)
    }

    suspend fun getMovie(id: Int): MovieDetail {
        return movieAPI.getMovie(id, API_KEY).body()!!
    }

    suspend fun getNowPlaying(pageIndex: Int = 1): MoviePage {
        return movieAPI.getNowPlaying(API_KEY, pageIndex).body()!!
    }

    suspend fun getPopular(pageIndex: Int = 1): MoviePage {
        return movieAPI.getPopular(API_KEY, pageIndex).body()!!
    }
}