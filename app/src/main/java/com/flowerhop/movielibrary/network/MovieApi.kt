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
        const val PATH_CATEGORY = "category"
        const val PAGE = "page"
        const val API_KEY = "api_key"
        const val LANGUAGE = "language"
    }

    @GET("movie/{$PATH_MOVIE_ID}")
    suspend fun getMovie(@Path(PATH_MOVIE_ID) id: Int,
                         @Query(API_KEY) apiKey: String, @Query(LANGUAGE) language: String = "en-US"): Response<MovieDetail>

    @GET("movie/{$PATH_CATEGORY}")
    suspend fun getList(@Path(PATH_CATEGORY) category: String,
                        @Query(API_KEY) apiKey: String,
                        @Query(PAGE) pageIndex: Int,
                        @Query(LANGUAGE) language: String = "en-US"): Response<MoviePage>
}