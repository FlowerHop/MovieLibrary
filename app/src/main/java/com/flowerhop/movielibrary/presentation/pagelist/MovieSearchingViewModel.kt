package com.flowerhop.movielibrary.presentation.pagelist

import android.util.Log
import androidx.lifecycle.*
import com.flowerhop.movielibrary.domain.model.Movie
import com.flowerhop.movielibrary.domain.model.MovieCategory.NowPlaying
import com.flowerhop.movielibrary.domain.repository.MyFavoritesRepository
import com.flowerhop.movielibrary.domain.usecase.GetCategoryListUseCase
import com.flowerhop.movielibrary.domain.usecase.SearchAtPageUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieSearchingViewModel(
    private val searchAtPageUseCase: SearchAtPageUseCase,
    private val getCategoryListUseCase: GetCategoryListUseCase,
    private val myFavoritesRepository: MyFavoritesRepository
): ViewModel() {
    private var loadedPage = 0
    private var searchString: String = ""

    private val _movies: MutableLiveData<MutableList<Movie>> = MediatorLiveData<MutableList<Movie>>().apply {
        value = mutableListOf()
        addSource(myFavoritesRepository.getIdListLiveData()) { idList ->
            val newList = value?.map { movie ->
                movie.copy(
                    myFavorite = idList.contains(movie.id)
                )
            }?.toMutableList()

            value = newList
        }
    }
    private val _defaultMovies: MutableLiveData<MutableList<Movie>> = MediatorLiveData<MutableList<Movie>>().apply {
        value = mutableListOf()
        viewModelScope.launch(Dispatchers.IO) {
            getCategoryListUseCase(1, DEFAULT_CATEGORY)?.let {
                postValue(it.results.toMutableList())
            }
        }

        addSource(myFavoritesRepository.getIdListLiveData()) { idList ->
            val newList = value?.map { movie ->
                movie.copy(
                    myFavorite = idList.contains(movie.id)
                )
            }?.toMutableList()

            value = newList
        }
    }

    val movies: LiveData<MutableList<Movie>> = _movies
    val defaultMovies: LiveData<MutableList<Movie>> = _defaultMovies

    fun search(searchString: String) {
        loadedPage = 0
        this.searchString = searchString

        if (loadDefaultPage()) {
            _movies.value = mutableListOf()
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            searchAtPageUseCase(
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
        if (loadDefaultPage()) return
        _movies.value?.let {
            if (!needLoadMore(it.size, lastVisiblePosition)) return
            loadPage(loadedPage + 1)
        }
    }

    fun addFavorite(id: Int) {
        myFavoritesRepository.add(id)
    }

    fun removeFavorite(id: Int) {
        myFavoritesRepository.remove(id)
    }

    private fun loadPage(pageIndex: Int) {
        logMsg("loadPage", "pageIndex = $pageIndex")
        if (pageIndex <= loadedPage) return
        loadedPage = pageIndex
        viewModelScope.launch(Dispatchers.IO) {
            searchAtPageUseCase(
                pageIndex = pageIndex,
                query = searchString
            )?.apply {
                _movies.value?.addAll(results)
                _movies.postValue(_movies.value)
            }
        }
    }

    private fun loadDefaultPage(): Boolean = searchString.isEmpty()

    companion object {
        private const val DEBUG = true
        private const val TAG = "MovieSearchingViewModel"
        val DEFAULT_CATEGORY = NowPlaying
        private fun logMsg(scope: String, msg: String) {
            if (!DEBUG) return
            Log.d(TAG, "[$scope] - $msg")
        }

        private fun needLoadMore(totalSize: Int, lastVisiblePosition: Int): Boolean {
            return lastVisiblePosition >= totalSize - 15
        }
    }
}