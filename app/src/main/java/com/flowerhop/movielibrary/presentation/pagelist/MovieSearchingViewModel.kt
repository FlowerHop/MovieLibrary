package com.flowerhop.movielibrary.presentation.pagelist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flowerhop.movielibrary.domain.model.Movie
import com.flowerhop.movielibrary.domain.usecase.SearchAtPageUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieSearchingViewModel(
    private val useCase: SearchAtPageUseCase
): ViewModel() {
    private var loadedPage = 0
    private var searchString: String = ""

    private val _movies: MutableLiveData<MutableList<Movie>> = MutableLiveData(mutableListOf())

    val movies: LiveData<MutableList<Movie>> = _movies

    fun search(searchString: String) {
        loadedPage = 0
        this.searchString = searchString

        viewModelScope.launch(Dispatchers.IO) {
            useCase(
                pageIndex = ++loadedPage,
                query = searchString
            )?.apply {
                _movies.value?.let {
                    it.clear()
                    it.addAll(results)
                }
                _movies.postValue(_movies.value)
            }
        }
    }

    fun loadMoreIfNeed(lastVisiblePosition: Int) {
        _movies.value?.let {
            if (!needLoadMore(it.size, lastVisiblePosition)) return
            loadPage(loadedPage + 1)
        }
    }

    private fun loadPage(pageIndex: Int) {
        logMsg("loadPage", "pageIndex = $pageIndex")
        if (pageIndex <= loadedPage) return
        loadedPage = pageIndex
        viewModelScope.launch(Dispatchers.IO) {
            useCase(
                pageIndex = pageIndex,
                query = searchString
            )?.apply {
                _movies.value?.addAll(results)
                _movies.postValue(_movies.value)
            }
        }
    }

    companion object {
        private const val DEBUG = true
        private const val TAG = "MovieSearchingViewModel"
        private fun logMsg(scope: String, msg: String) {
            if (!DEBUG) return
            Log.d(TAG, "[$scope] - $msg")
        }

        private fun needLoadMore(totalSize: Int, lastVisiblePosition: Int): Boolean {
            return lastVisiblePosition >= totalSize - 15
        }
    }
}