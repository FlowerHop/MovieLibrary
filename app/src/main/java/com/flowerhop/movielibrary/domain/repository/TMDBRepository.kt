package com.flowerhop.movielibrary.domain.repository

import com.flowerhop.movielibrary.data.dto.Genre
import com.flowerhop.movielibrary.data.dto.MovieDetailDto
import com.flowerhop.movielibrary.data.dto.MoviePageDto
import com.flowerhop.movielibrary.domain.model.MovieCategory
import retrofit2.Response

interface TMDBRepository {
    suspend fun getMovieById(id: Int): Response<MovieDetailDto>
    suspend fun getCategoryListAtPage(category: MovieCategory, pageIndex: Int): Response<MoviePageDto>
    suspend fun searchAtPage(query: String, pageIndex: Int): Response<MoviePageDto>
    suspend fun discoverGenresAtPage(genres: List<Genre>, pageIndex: Int): Response<MoviePageDto>
}