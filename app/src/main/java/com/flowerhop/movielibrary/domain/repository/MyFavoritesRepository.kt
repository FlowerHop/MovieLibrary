package com.flowerhop.movielibrary.domain.repository

import androidx.lifecycle.LiveData
import com.flowerhop.movielibrary.data.dto.MovieDetailDto

interface MyFavoritesRepository {
    fun init(onSuccess: (Unit) -> Unit, onError: (Unit) -> Unit)
    fun add(movieId: Int)
    fun remove(movieId: Int)
    suspend fun getList(): List<MovieDetailDto>
    fun contains(movieId: Int): Boolean
    fun getIdListLiveData(): LiveData<List<Int>>
}