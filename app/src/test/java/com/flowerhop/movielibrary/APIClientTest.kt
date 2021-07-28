package com.flowerhop.movielibrary

import com.flowerhop.movielibrary.network.APIClient
import com.flowerhop.movielibrary.network.entity.MovieDetail
import com.flowerhop.movielibrary.network.entity.MoviePage
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class APIClientTest {
    private lateinit var api: APIClient
    @Before
    fun initAPI() {
        api = APIClient()
    }

    @Test
    fun `Get a movie which id is 99`() {
        val movieDetail: MovieDetail = runBlocking {
            api.getMovie(99)
        }

        Assert.assertEquals(99, movieDetail.id)
    }

    @Test
    fun `Get now playing movies at page 2, should get page = 2`() {
        val expected = 2
        val moviePage: MoviePage = runBlocking {
            api.getNowPlaying(2)
        }

        Assert.assertEquals(expected, moviePage.page)
    }

    @Test
    fun `Get popular movies at page 3, should get page = 3`() {
        val expected = 3
        val moviePage: MoviePage = runBlocking {
            api.getPopular(3)
        }

        Assert.assertEquals(expected, moviePage.page)
    }
}