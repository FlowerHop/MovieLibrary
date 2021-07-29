package com.flowerhop.movielibrary.network

import android.util.Log
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
        val response = movieAPI.getMovie(id, API_KEY)

        try {
            if (response.isSuccessful)
                return response.body()!!
            else
                TODO("Throw exception")
        } catch (e: Exception) {
            Log.e(TAG, "getMovie: failed $e" )
            throw e
        }
    }

    suspend fun getNowPlaying(pageIndex: Int = 1): MoviePage {
        val response = movieAPI.getNowPlaying(API_KEY, pageIndex)

        try {
            if (response.isSuccessful)
                return response.body()!!
            else
                TODO("Throw exception")
        } catch (e: Exception) {
            Log.e(TAG, "getNowPlaying: failed $e")
            throw e
        }
    }

    suspend fun getPopular(pageIndex: Int = 1): MoviePage {
        val response = movieAPI.getPopular(API_KEY, pageIndex)

        try {
            if (response.isSuccessful)
                return response.body()!!
            else
                TODO("Throw exception")
        } catch (e: Exception) {
            Log.e(TAG, "getPopular: failed $e")
            throw e
        }
    }

    suspend fun getTopRated(pageIndex: Int = 1): MoviePage {
        val response = movieAPI.getTopRated(API_KEY, pageIndex)

        try {
            if (response.isSuccessful)
                return response.body()!!
            else
                TODO("Throw exception")
        } catch (e: Exception) {
            Log.e(TAG, "getTopRated: failed $e")
            throw e
        }
    }
}