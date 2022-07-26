package com.flowerhop.movielibrary.domain.repository

import com.flowerhop.movielibrary.data.dto.MovieDetailDto

interface MyFavoritesRepository {
    suspend fun add(movieId: Int)
    suspend fun remove(movieId: Int)
    suspend fun getList(): List<MovieDetailDto>
}