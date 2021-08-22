package com.flowerhop.movielibrary.viewmodel

import androidx.lifecycle.*
import com.flowerhop.movielibrary.repository.MovieRepository
import com.flowerhop.movielibrary.view.MovieCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoviesViewModel(private val repo: MovieRepository): ViewModel() {
    private val listMap: MutableMap<MovieCategory, MovieList> = mutableMapOf()
    val refreshing = MediatorLiveData<Boolean>()

    init {
        val observer = Observer<Boolean> {
            listMap.toList().map {
                return@map it.second.refreshing.value
            }.reduce { acc, b -> acc!! && b!! }
        }
        MovieCategory.values().forEach {
            val movieList = MovieList()
            listMap[it] = movieList
            refreshing.addSource(movieList.refreshing, observer)
        }
    }

    fun refresh() {
        refreshing.postValue(false)
        listMap.forEach { (key, movieList) ->
            movieList.refreshing.postValue(true)
            movieList.movies.postValue(listOf())
            viewModelScope.launch(Dispatchers.IO) {
                val moviePage = repo.getList(key, 1)
                movieList.movies.postValue(moviePage.movies)
                movieList.refreshing.postValue(false)
            }
        }
    }

    fun getList(category: MovieCategory): MovieList {
        return listMap[category]!!
    }
}