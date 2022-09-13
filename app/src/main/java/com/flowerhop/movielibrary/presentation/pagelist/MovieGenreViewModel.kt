package com.flowerhop.movielibrary.presentation.pagelist

import android.util.Log
import androidx.lifecycle.*
import com.flowerhop.movielibrary.data.dto.Genre
import com.flowerhop.movielibrary.domain.model.Movie
import com.flowerhop.movielibrary.domain.repository.MyFavoritesRepository
import com.flowerhop.movielibrary.domain.usecase.DiscoverMoviesWithGenres
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieGenreViewModel(
    private val genre: Genre,
    private val useCase: DiscoverMoviesWithGenres,
    private val myFavoritesRepository: MyFavoritesRepository
): ViewModel() {
    private var loadedPage = 0

    private val _movies = MediatorLiveData<MutableList<Movie>>().apply {
        value = mutableListOf()
        loadPage(1)

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

    fun loadMoreIfNeed(lastVisiblePosition: Int) {
        _movies.value?.let {
            if (!needLoadMore(it.size, lastVisiblePosition)) return
            loadPage(loadedPage + 1)
        }
    }

    private fun loadPage(pageIndex: Int) {
        logMsg("loadPage", "pageIndex = $pageIndex")
        if (pageIndex <= loadedPage) return
        loadedPage++
        viewModelScope.launch(Dispatchers.IO) {
            useCase(
                pageIndex = pageIndex,
                genres = listOf(genre)
            )?.apply {
                _movies.value?.addAll(results)
                _movies.postValue(_movies.value)
            }
        }
    }

    fun addFavorite(id: Int) {
        myFavoritesRepository.add(id)
    }

    fun removeFavorite(id: Int) {
        myFavoritesRepository.remove(id)
    }

    companion object {
        private const val DEBUG = true
        private const val TAG = "MovieGenreViewModel"
        private fun logMsg(scope: String, msg: String) {
            if (!DEBUG) return
            Log.d(TAG, "[$scope] - $msg")
        }

        private fun needLoadMore(totalSize: Int, lastVisiblePosition: Int): Boolean {
            return lastVisiblePosition >= totalSize - 15
        }
    }
}