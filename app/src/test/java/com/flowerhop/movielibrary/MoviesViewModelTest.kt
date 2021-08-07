package com.flowerhop.movielibrary

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MoviesViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {

    }

    @Test
    fun `Can get movie list`() {
        // Arrange
        val moviesViewModel = MoviesViewModel(MovieRepository())

        // Act
        moviesViewModel.nowPlayings.observeForever {
            // Assert
            println("sss")
            Assert.assertNull(moviesViewModel.nowPlayings)
        }
    }
}