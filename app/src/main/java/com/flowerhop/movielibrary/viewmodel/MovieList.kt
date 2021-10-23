package com.flowerhop.movielibrary.viewmodel

import androidx.lifecycle.MutableLiveData
import com.flowerhop.movielibrary.network.entity.Movie

@Deprecated("Unused")
class MovieList(list: List<Movie> = listOf()) {
    val refreshing: MutableLiveData<Boolean> = MutableLiveData(false)
    val movies: MutableLiveData<List<Movie>> = MutableLiveData<List<Movie>>(list)
}
