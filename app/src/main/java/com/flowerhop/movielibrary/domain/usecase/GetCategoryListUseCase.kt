package com.flowerhop.movielibrary.domain.usecase

import android.accounts.NetworkErrorException
import android.util.Log
import com.flowerhop.movielibrary.domain.model.MoviePage
import com.flowerhop.movielibrary.domain.repository.TMDBRepository
import com.flowerhop.movielibrary.view.MovieCategory

class GetCategoryListUseCase(
    private val tmdbRepository: TMDBRepository
) {
    suspend operator fun invoke(pageIndex: Int, category: MovieCategory): MoviePage {
        val response = tmdbRepository.getCategoryListAtPage(
            pageIndex = pageIndex,
            category = category
        )

        try {
            if (response.isSuccessful)
                return response.body()!!
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