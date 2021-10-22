package com.flowerhop.movielibrary.presentation.categorylist

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

    fun loadNewPage() {
        loadPage(loadedPage++)
    }

    fun loadMoreIfNeed(position: Int) {
        movies.value?.let {
            if (position < it.size - 15) return
            loadNewPage()
        }
    }

    private fun loadPage(pageIndex: Int) {
        if (pageIndex == loadedPage) return
        viewModelScope.launch {
            val list = useCase(
                pageIndex = pageIndex,
                category = category)
            movies.value?.addAll(list.results)
            movies.postValue(movies.value)
        }
    }
}