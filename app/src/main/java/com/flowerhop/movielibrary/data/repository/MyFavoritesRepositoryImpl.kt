package com.flowerhop.movielibrary.data.repository

import com.flowerhop.movielibrary.data.dto.MovieDetailDto
import com.flowerhop.movielibrary.domain.repository.MyFavoritesRepository
import com.flowerhop.movielibrary.domain.repository.TMDBRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.util.concurrent.CopyOnWriteArraySet

class MyFavoritesRepositoryImpl(
    private val api: TMDBRepository
): MyFavoritesRepository {
    private val idList = CopyOnWriteArraySet<Int>()

    override suspend fun add(movieId: Int) {
        idList.add(movieId)
    }

    override suspend fun remove(movieId: Int) {
        idList.remove(movieId)
    }

    override suspend fun getList(): List<MovieDetailDto> = withContext(Dispatchers.IO) {
        val jobs = idList.map { async { api.getMovieById(it) } }
        jobs.mapNotNull { it.await().body() }

        idList.map {
            async {
                api.getMovieById(it)
            }
        }.mapNotNull {
            it.await().body()
        }
    }
}