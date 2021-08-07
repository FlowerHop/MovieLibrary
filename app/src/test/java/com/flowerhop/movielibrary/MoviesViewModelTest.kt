package com.flowerhop.movielibrary

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.flowerhop.movielibrary.network.entity.Movie
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MoviesViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `Can notify movie list change`() {
        // Arrange
        var called = false
        val moviesViewModel = MoviesViewModel(MovieRepository())
        val observer = Observer<List<Movie>>() {
            called = true
        }
        // Act
        moviesViewModel.nowPlayings.observeForever(observer)
        Thread.sleep(2000)
        Assert.assertTrue(called)
        moviesViewModel.nowPlayings.removeObserver(observer)
    }
}