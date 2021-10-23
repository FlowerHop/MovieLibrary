package com.flowerhop.movielibrary.data.repository

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
        const val TAG = "APIClient"
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
        const val API_KEY = "a56648c829ad25080106ba1c138c3e0b"
    }

    override suspend fun getMovieById(id: Int): Response<MovieDetailDto> {
        return api.getMovieById(
            id = id,
            apiKey = API_KEY)
    }

    override suspend fun getCategoryListAtPage(category: MovieCategory, pageIndex: Int): Response<MoviePageDto> {
        return api.getCategoryList(
            category = category.path,
            pageIndex = pageIndex,
            apiKey = API_KEY)
    }
}