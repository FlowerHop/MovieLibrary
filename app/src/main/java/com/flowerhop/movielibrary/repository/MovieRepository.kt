package com.flowerhop.movielibrary.repository

import com.flowerhop.movielibrary.network.APIClient
import com.flowerhop.movielibrary.network.entity.MovieDetail
import com.flowerhop.movielibrary.network.entity.MoviePage
import com.flowerhop.movielibrary.domain.model.MovieCategory

@Deprecated("Unused")
class MovieRepository {
    private val apiClient = APIClient()

    suspend fun getMovie(id: Int): MovieDetail {
        return apiClient.getMovie(id)
    }

    suspend fun getList(category: MovieCategory, pageIndex: Int): MoviePage {
        return apiClient.getList(category)
    }
}