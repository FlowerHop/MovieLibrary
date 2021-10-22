package com.flowerhop.movielibrary.domain.repository

import com.flowerhop.movielibrary.domain.model.MovieDetail
import com.flowerhop.movielibrary.domain.model.MoviePage
import com.flowerhop.movielibrary.view.MovieCategory
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBRepository {
    suspend fun getMovieById(id: Int): Response<MovieDetail>
    suspend fun getCategoryListAtPage(category: MovieCategory, pageIndex: Int): Response<MoviePage>
}