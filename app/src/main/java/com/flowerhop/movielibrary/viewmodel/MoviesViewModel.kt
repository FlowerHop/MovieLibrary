package com.flowerhop.movielibrary.viewmodel

import androidx.lifecycle.*
import com.flowerhop.movielibrary.repository.MovieRepository
import com.flowerhop.movielibrary.network.entity.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoviesViewModel(val repo: MovieRepository): ViewModel() {
    var nowPlayings = MutableLiveData<List<Movie>>().apply {
        viewModelScope.launch(Dispatchers.IO) {
            postValue(repo.getNowPlaying(1).movies)
        }
    }

    var populars = MutableLiveData<List<Movie>>().apply {
        viewModelScope.launch(Dispatchers.IO) {
            postValue(repo.getPopular(1).movies)
        }
    }

    var topRatedList = MutableLiveData<List<Movie>>().apply {
        viewModelScope.launch(Dispatchers.IO) {
            postValue(repo.getTopRated(1).movies)
        }
    }

    fun refresh() {
        nowPlayings.postValue(listOf())
        populars.postValue(listOf())
        topRatedList.postValue(listOf())

        viewModelScope.launch(Dispatchers.IO) {
            nowPlayings.postValue(repo.getNowPlaying(1).movies)
            populars.postValue(repo.getPopular(1).movies)
            topRatedList.postValue(repo.getTopRated(1).movies)
        }
    }
}