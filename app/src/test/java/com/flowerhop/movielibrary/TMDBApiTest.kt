package com.flowerhop.movielibrary

import com.flowerhop.movielibrary.comman.Constants
import com.flowerhop.movielibrary.data.dto.toMovieDetail
import com.flowerhop.movielibrary.data.dto.toMoviePage
import com.flowerhop.movielibrary.data.remote.TMDBApi
import com.flowerhop.movielibrary.di.Providers
import com.flowerhop.movielibrary.domain.model.MovieCategory
import com.flowerhop.movielibrary.domain.model.MovieDetail
import com.flowerhop.movielibrary.domain.model.MoviePage
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class TMDBApiTest {
    private lateinit var api: TMDBApi
    @Before
    fun initAPI() {
        api = Providers.provideTMDBApi()
    }

    @Test
    fun `Get a movie which id is 99`() {
        val movieDetail: MovieDetail? = runBlocking {
            val result = api.getMovieById(99, Constants.API_KEY)
            if (result.isSuccessful)
                result.body()?.toMovieDetail()
            else null
        }

        Assert.assertEquals(99, movieDetail?.id)
    }

    @Test
    fun `Get now playing movies at page 2, should get page = 2`() {
        val expected = 2
        val moviePage: MoviePage? = runBlocking {
            val result = api.getCategoryList(MovieCategory.NowPlaying.path, Constants.API_KEY, 2)
            if (result.isSuccessful)
                result.body()?.toMoviePage()
            else null
        }

        Assert.assertEquals(expected, moviePage?.page)
    }

    @Test
    fun `Get popular movies at page 3, should get page = 3`() {
        val expected = 3
        val moviePage: MoviePage? = runBlocking {
            val result = api.getCategoryList(MovieCategory.Popular.path, Constants.API_KEY, 3)
            if (result.isSuccessful)
                result.body()?.toMoviePage()
            else null
        }

        Assert.assertEquals(expected, moviePage?.page)
    }

    @Test
    fun `Get top rated movies at page 1, should get page = 1`() {
        val expected = 1
        val moviePage: MoviePage? = runBlocking {
            val result = api.getCategoryList(MovieCategory.TopRated.path, Constants.API_KEY, 1)
            if (result.isSuccessful)
                result.body()?.toMoviePage()
            else null
        }

        Assert.assertEquals(expected, moviePage?.page)
    }

    @Test
    fun `Search spider movies should get something`() {
        val expectedSuccessful = true
        var isSuccess = false
        runBlocking {
            val result = api.searchMovies(
                apiKey = Constants.API_KEY,
                query = "spider",
                pageIndex = 1
            )

            isSuccess = result.isSuccessful
        }

        Assert.assertEquals(expectedSuccessful, isSuccess)
    }

    @Test
    fun `Discover movies with a genre(id = 18)`() {
        val expectedSuccessful = true
        var isSuccess = false
        runBlocking {
            val result = api.discoverMovies(
                apiKey = Constants.API_KEY,
                withGenres = listOf("18").joinToString(),
                pageIndex = 1
            )
print(result.body())
            isSuccess = result.isSuccessful
        }

        Assert.assertEquals(expectedSuccessful, isSuccess)
    }
}