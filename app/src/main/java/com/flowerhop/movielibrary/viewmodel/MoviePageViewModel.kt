package com.flowerhop.movielibrary.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flowerhop.movielibrary.network.entity.MoviePage
import com.flowerhop.movielibrary.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoviePageViewModel(private val repo: MovieRepository): ViewModel() {
    private var loadedPage = 1

    var pageList = MutableLiveData<MutableList<MoviePage>>(mutableListOf()).apply {
        loadNewPage()
    }

    fun loadNewPage() {
        loadPage(loadedPage++)
    }

    private fun loadPage(pageIndex: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val newList = mutableListOf<MoviePage>()
            newList.add(repo.getNowPlaying(pageIndex))
            pageList.value?.addAll(newList)
            pageList.postValue(pageList.value)
        }
    }
}