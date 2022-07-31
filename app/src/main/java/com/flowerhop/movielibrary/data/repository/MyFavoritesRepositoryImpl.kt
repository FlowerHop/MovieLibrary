package com.flowerhop.movielibrary.data.repository

import com.flowerhop.movielibrary.data.dto.MovieDetailDto
import com.flowerhop.movielibrary.domain.repository.MyFavoritesRepository
import com.flowerhop.movielibrary.domain.repository.TMDBRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.util.concurrent.CopyOnWriteArraySet

// The data will only stored locally, but now just stored in memory
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

    override fun contains(movieId: Int): Boolean = idList.contains(movieId)

    private suspend fun writeToDisk() = withContext(Dispatchers.IO) {
        // TODO write to disk
        // write id?

    }
}