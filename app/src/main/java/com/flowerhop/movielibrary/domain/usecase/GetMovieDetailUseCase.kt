package com.flowerhop.movielibrary.domain.usecase

import android.util.Log
import com.flowerhop.movielibrary.data.dto.toMovieDetail
import com.flowerhop.movielibrary.domain.model.MovieDetail
import com.flowerhop.movielibrary.domain.repository.MyFavoritesRepository
import com.flowerhop.movielibrary.domain.repository.TMDBRepository

class GetMovieDetailUseCase(
    private val tmdbRepository: TMDBRepository,
    private val myFavoritesRepository: MyFavoritesRepository
) {
    suspend operator fun invoke(id: Int): MovieDetail? {
        val response = tmdbRepository.getMovieById(id)
        try {
            if (response.isSuccessful)
                return response.body()?.toMovieDetail(myFavoritesRepository.contains(id))
            else
                TODO("Throw exception")
        } catch (e: Exception) {
            Log.e(TAG, "getMovie: failed $e" )
            throw e
        }
    }

    companion object {
        private const val TAG = "GetMovieDetailUseCase"
    }
}