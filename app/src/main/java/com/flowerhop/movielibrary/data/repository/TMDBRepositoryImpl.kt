package com.flowerhop.movielibrary.data.repository

import com.flowerhop.movielibrary.comman.Constants
import com.flowerhop.movielibrary.data.dto.MovieDetailDto
import com.flowerhop.movielibrary.data.dto.MoviePageDto
import com.flowerhop.movielibrary.data.remote.TMDBApi
import com.flowerhop.movielibrary.domain.repository.TMDBRepository
import com.flowerhop.movielibrary.domain.model.MovieCategory
import retrofit2.Response

class TMDBRepositoryImpl(
    val api: TMDBApi
): TMDBRepository {
    companion object {
        const val TAG = "TMDBRepositoryImpl"
    }

    override suspend fun getMovieById(id: Int): Response<MovieDetailDto> {
        return api.getMovieById(
            id = id,
            apiKey = Constants.API_KEY)
    }

    override suspend fun getCategoryListAtPage(category: MovieCategory, pageIndex: Int): Response<MoviePageDto> {
        return api.getCategoryList(
            category = category.path,
            pageIndex = pageIndex,
            apiKey = Constants.API_KEY
        )
    }

    override suspend fun searchAtPage(query: String, pageIndex: Int): Response<MoviePageDto> {
        return api.searchMovies(
            query = query,
            pageIndex = pageIndex,
            apiKey = Constants.API_KEY
        )
    }
}