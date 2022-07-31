package com.flowerhop.movielibrary

import com.flowerhop.movielibrary.data.dto.toMovieDetail
import com.flowerhop.movielibrary.di.Providers
import com.flowerhop.movielibrary.domain.repository.TMDBRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class MyFavoritesRepositoryTest {
    private lateinit var tmdbRepository: TMDBRepository

    @Before
    fun initAPI() {
        val api = Providers.provideTMDBApi()
        tmdbRepository = Providers.provideTMDBRepository(api)
    }

    @Test
    fun `Favorite a movie which id is 99, myFavorite should be true`() {
        val myFavoritesRepository = Providers.provideFavoritesRepository(tmdbRepository)
        runBlocking {
            myFavoritesRepository.add(99)
        }

        val movieDetail = runBlocking {
            val result = tmdbRepository.getMovieById(99)
            if (result.isSuccessful) {
                result.body()?.toMovieDetail(myFavoritesRepository.contains(99))
            } else {
                null
            }
        }

        Assert.assertEquals(true, movieDetail?.myFavorite)
    }

    @Test
    fun `Favorite and remove a movie which id is 99, myFavorite should be false`() {
        val myFavoritesRepository = Providers.provideFavoritesRepository(tmdbRepository)
        runBlocking {
            myFavoritesRepository.add(99)
            myFavoritesRepository.remove(99)
        }

        val movieDetail = runBlocking {
            val result = tmdbRepository.getMovieById(99)
            if (result.isSuccessful) {
                result.body()?.toMovieDetail(myFavoritesRepository.contains(99))
            } else {
                null
            }
        }

        movieDetail?.let {
            Assert.assertEquals(false, it.myFavorite)
        } ?: Assert.fail("No fetched result")

    }

    @Test
    fun `Favorite a movie which id is 99, getList() should contains it`() {
        val myFavoritesRepository = Providers.provideFavoritesRepository(tmdbRepository)
        runBlocking {
            myFavoritesRepository.add(99)
            val results = myFavoritesRepository.getList()
            results.filter { it.id == 99 }.getOrNull(0)?.let {
                Assert.assertTrue(true)
            } ?: Assert.fail("No result")
        }
    }
}