package com.flowerhop.movielibrary.viewmodel

import androidx.lifecycle.*
import com.flowerhop.movielibrary.repository.MovieRepository
import com.flowerhop.movielibrary.network.entity.Movie
import com.flowerhop.movielibrary.view.MovieCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoviesViewModel(val repo: MovieRepository): ViewModel() {
    private val listMap: MutableMap<MovieCategory, MutableLiveData<List<Movie>>> = mutableMapOf(
        MovieCategory.NowPlaying to MutableLiveData(listOf<Movie>()),
        MovieCategory.TopRated to MutableLiveData(listOf<Movie>()),
        MovieCategory.Popular to MutableLiveData(listOf<Movie>()),
    )

    fun refresh() {
        listMap.forEach { (key, liveData) -> liveData.postValue(listOf())}

        viewModelScope.launch(Dispatchers.IO) {
            listMap.forEach{(key, liveData) ->
                viewModelScope.launch(Dispatchers.IO) {
                    val list = repo.getList(key, 1).movies

                    withContext(Dispatchers.Main) {
                        liveData.postValue(list)
                    }
                }
            }
        }
    }

    fun getList(category: MovieCategory): MutableLiveData<List<Movie>> {
        val list: MutableLiveData<List<Movie>> = listMap[category] ?: MutableLiveData(listOf())
        if (listMap[category] == null) {
            listMap[category] = list
        }

        return list
    }
}