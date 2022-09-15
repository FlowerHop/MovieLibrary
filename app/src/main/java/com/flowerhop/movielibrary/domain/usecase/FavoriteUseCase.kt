package com.flowerhop.movielibrary.domain.usecase

import androidx.lifecycle.LiveData
import com.flowerhop.movielibrary.domain.repository.MyFavoritesRepository

class FavoriteUseCase(
    private val favoritesRepository: MyFavoritesRepository
) {
    val favoriteMovieIdsLiveData: LiveData<List<Int>>
        get() = favoritesRepository.getIdListLiveData()

    fun add(movieId: Int) {
        favoritesRepository.add(movieId)
    }

    fun remove(movieId: Int) {
        favoritesRepository.remove(movieId)
    }
}