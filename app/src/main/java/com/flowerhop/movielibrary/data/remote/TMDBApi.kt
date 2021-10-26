package com.flowerhop.movielibrary.data.remote

import com.flowerhop.movielibrary.data.dto.MovieDetailDto
import com.flowerhop.movielibrary.data.dto.MoviePageDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApi {
    companion object {
        const val PATH_MOVIE_ID = "movie_id"
        const val PATH_CATEGORY = "category"
        const val PAGE = "page"
        const val API_KEY = "api_key"
        const val LANGUAGE = "language"
        const val QUERY = "query"
        const val INCLUDE_ADULT = "include_adult"
        const val REGION = "region"
        const val YEAR = "year"
        const val PRIMARY_RELEASE_YEAR = "primary_release_year"
    }

    @GET("movie/{$PATH_MOVIE_ID}")
    suspend fun getMovieById(@Path(PATH_MOVIE_ID) id: Int,
                         @Query(API_KEY) apiKey: String, @Query(LANGUAGE) language: String = "en-US"): Response<MovieDetailDto>

    @GET("movie/{$PATH_CATEGORY}")
    suspend fun getCategoryList(@Path(PATH_CATEGORY) category: String,
                                @Query(API_KEY) apiKey: String,
                                @Query(PAGE) pageIndex: Int,
                                @Query(LANGUAGE) language: String = "en-US"): Response<MoviePageDto>

    @GET("search/movie")
    suspend fun searchMovies(@Query(API_KEY) apiKey: String,
                             @Query(LANGUAGE) language: String = "en-US",
                             @Query(QUERY) query: String,
                             @Query(PAGE) pageIndex: Int,
                             @Query(INCLUDE_ADULT) includeAdult:Boolean = true,
                             @Query(REGION) region: String? = null,
                             @Query(YEAR) year: Int? = null,
                             @Query(PRIMARY_RELEASE_YEAR) primaryReleaseYear: Int? = null): Response<MoviePageDto>
}