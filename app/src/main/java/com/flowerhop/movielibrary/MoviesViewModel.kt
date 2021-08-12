package com.flowerhop.movielibrary

import androidx.lifecycle.*
import com.flowerhop.movielibrary.network.entity.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoviesViewModel(val repo: MovieRepository): ViewModel() {
    var nowPlayings = MutableLiveData<List<Movie>>().apply {
        viewModelScope.launch(Dispatchers.IO) {
            postValue(repo.getNowPlaying(1).movies)
        }
    }

    fun refresh() {
        nowPlayings.postValue(listOf())

        viewModelScope.launch(Dispatchers.IO) {
            nowPlayings.postValue(repo.getNowPlaying(1).movies)
        }
    }
}