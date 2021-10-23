package com.flowerhop.movielibrary.presentation.categorylist

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flowerhop.movielibrary.domain.model.Movie
import com.flowerhop.movielibrary.domain.usecase.GetCategoryListUseCase
import com.flowerhop.movielibrary.view.MovieCategory
import kotlinx.coroutines.launch

class MovieCategoryViewModel(
    private val category: MovieCategory,
    private val useCase: GetCategoryListUseCase
): ViewModel() {
    private var loadedPage = 0

    var movies = MutableLiveData<MutableList<Movie>>(mutableListOf()).apply {
        loadPage(1)
    }

    fun loadMoreIfNeed(position: Int) {
        movies.value?.let {
            if (position < it.size - 15) return
            loadPage(loadedPage + 1)
        }
    }

    private fun loadPage(pageIndex: Int) {
        logMsg("loadPage", "pageIndex = $pageIndex")
        if (pageIndex <= loadedPage) return
        loadedPage++
        viewModelScope.launch {
            useCase(
                pageIndex = pageIndex,
                category = category
            )?.apply {
                movies.value?.addAll(results)
                movies.postValue(movies.value)
            }
        }
    }

    companion object {
        private const val DEBUG = true
        private const val TAG = "MovieCategoryVM"
        private fun logMsg(scope: String, msg: String) {
            if (!DEBUG) return
            Log.d(TAG, "[$scope] - $msg")
        }
    }
}