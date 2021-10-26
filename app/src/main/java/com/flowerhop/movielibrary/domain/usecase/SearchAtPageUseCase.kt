package com.flowerhop.movielibrary.domain.usecase

import android.util.Log
import com.flowerhop.movielibrary.data.dto.toMoviePage
import com.flowerhop.movielibrary.domain.model.MoviePage
import com.flowerhop.movielibrary.domain.repository.TMDBRepository
import java.lang.Exception

class SearchAtPageUseCase(
    private val tmdbRepository: TMDBRepository
) {
    suspend operator fun invoke(query: String, pageIndex: Int): MoviePage? {
        val response = tmdbRepository.searchAtPage(query, pageIndex)
        try {
            if (response.isSuccessful)
                return response.body()?.toMoviePage()
            else
                TODO("Throw exception")
        } catch (e: Exception) {
            Log.e(TAG, "searchAtPage: failed $e" )
            throw e
        }
    }

    companion object {
        private const val TAG = "SearchAtPageUseCase"
    }
}