package com.flowerhop.movielibrary.domain.usecase

import android.util.Log
import com.flowerhop.movielibrary.data.dto.Genre
import com.flowerhop.movielibrary.data.dto.toMoviePage
import com.flowerhop.movielibrary.domain.model.MoviePage
import com.flowerhop.movielibrary.domain.repository.TMDBRepository

class DiscoverMoviesWithGenres(
    private val tmdbRepository: TMDBRepository
) {
    suspend operator fun invoke(genres: List<Genre>, pageIndex: Int = 1): MoviePage? {
        val result = tmdbRepository.discoverGenresAtPage(genres, pageIndex)
        try {
            if (result.isSuccessful)
                return result.body()?.toMoviePage()
            else
                TODO("Throw exception")

        } catch (e: Exception) {
            Log.e(TAG, "discoverGenresAtPage: failed $e" )
            throw e
        }
    }

    companion object {
        private const val TAG = "DiscoverMovies"
    }
}