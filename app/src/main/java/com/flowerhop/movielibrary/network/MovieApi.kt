package com.flowerhop.movielibrary.network

import com.flowerhop.movielibrary.network.entity.MovieDetail
import com.flowerhop.movielibrary.network.entity.MoviePage
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    companion object {
        const val PATH_MOVIE_ID = "movie_id"
        const val PATH_NOW_PLAYING = "now_playing"
        const val PATH_POPULAR = "popular"
        const val PAGE = "page"
        const val API_KEY = "api_key"
        const val LANGUAGE = "language"
    }

    @GET("movie/{$PATH_MOVIE_ID}")
    suspend fun getMovie(@Path(PATH_MOVIE_ID) id: Int,
                         @Query(API_KEY) apiKey: String, @Query(LANGUAGE) language: String = "en-US"): Response<MovieDetail>


    // Region ?
    @GET("movie/$PATH_NOW_PLAYING")
    suspend fun getNowPlaying(@Query(API_KEY) apiKey: String,
                              @Query(PAGE) pageIndex: Int,
                              @Query(LANGUAGE) language: String = "en-US"): Response<MoviePage>

    @GET("movie/$PATH_POPULAR")
    suspend fun getPopular(@Query(API_KEY) apiKey: String,
                              @Query(PAGE) pageIndex: Int,
                              @Query(LANGUAGE) language: String = "en-US"): Response<MoviePage>
}