package com.flowerhop.movielibrary.domain.usecase

import android.util.Log
import com.flowerhop.movielibrary.data.dto.toMoviePage
import com.flowerhop.movielibrary.domain.model.MoviePage
import com.flowerhop.movielibrary.domain.repository.TMDBRepository
import com.flowerhop.movielibrary.domain.model.MovieCategory
import com.flowerhop.movielibrary.domain.repository.MyFavoritesRepository

class GetCategoryListUseCase(
    private val tmdbRepository: TMDBRepository,
    private val favoritesRepository: MyFavoritesRepository
) {
    suspend operator fun invoke(pageIndex: Int, category: MovieCategory): MoviePage? {
        val response = tmdbRepository.getCategoryListAtPage(
            pageIndex = pageIndex,
            category = category
        )

        try {
            if (response.isSuccessful)
                return response.body()?.toMoviePage {
                    favoritesRepository.contains(it)
                }
            else
                throw Exception(response.message())
        } catch (e: Exception) {
            Log.e(TAG, "get ${category.name} list: failed $e")
            throw e
        }
    }

    companion object {
        private const val TAG = "CategoryListUseCase"
    }
}