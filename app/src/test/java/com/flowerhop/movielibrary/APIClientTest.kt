package com.flowerhop.movielibrary

import com.flowerhop.movielibrary.network.APIClient
import com.flowerhop.movielibrary.network.entity.Movie
import com.flowerhop.movielibrary.network.entity.NowPlayingPage
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
        val movie: Movie = runBlocking {
            api.getMovie(99)
        }

        Assert.assertEquals(99, movie.id)
    }

    @Test
    fun `Get now playing movies at page 2, should get page = 2`() {
        val expected = 2
        val nowPlayingPage: NowPlayingPage = runBlocking {
            api.getNowPlaying(2)
        }

        Assert.assertEquals(expected, nowPlayingPage.page)
    }
}