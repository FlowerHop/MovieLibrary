package com.flowerhop.movielibrary.domain.repository

import com.flowerhop.movielibrary.data.dto.MovieDetailDto
import com.flowerhop.movielibrary.data.dto.MoviePageDto
import com.flowerhop.movielibrary.view.MovieCategory
import retrofit2.Response

interface TMDBRepository {
    suspend fun getMovieById(id: Int): Response<MovieDetailDto>
    suspend fun getCategoryListAtPage(category: MovieCategory, pageIndex: Int): Response<MoviePageDto>
}