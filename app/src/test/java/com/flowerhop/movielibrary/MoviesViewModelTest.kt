package com.flowerhop.movielibrary

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.flowerhop.movielibrary.di.Providers
import com.flowerhop.movielibrary.domain.model.Movie
import com.flowerhop.movielibrary.presentation.MoviesViewModel
import com.flowerhop.movielibrary.presentation.StatefulData
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
        var changed = false
        var firstObserve = true
        val tmdbApi = Providers.provideTMDBApi()
        val tmdbRepository = Providers.provideTMDBRepository(tmdbApi)
        val useCase = Providers.provideGetCategoryListUseCase(tmdbRepository)
        val moviesViewModel = MoviesViewModel(useCase)
        val observer = Observer<StatefulData<MutableList<Movie>>> {
            if (firstObserve) {
                firstObserve = false
                return@Observer
            }

            changed = true
        }

        // Act
        moviesViewModel.refresh()
        moviesViewModel.nowPlayingList.observeForever(observer)
        Thread.sleep(2000)

        // Assert
        Assert.assertTrue(changed)
        moviesViewModel.nowPlayingList.removeObserver(observer)
    }
}