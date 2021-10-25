package com.flowerhop.movielibrary.presentation

import androidx.lifecycle.*
import com.flowerhop.movielibrary.domain.model.Movie
import com.flowerhop.movielibrary.domain.usecase.GetCategoryListUseCase
import com.flowerhop.movielibrary.domain.model.MovieCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val useCase: GetCategoryListUseCase
): ViewModel() {
    private val _nowPlayingList: MutableLiveData<StatefulData<MutableList<Movie>>> by lazy {
        MutableLiveData(
            StatefulData(
                isLoading = true,
                data = mutableListOf()
            )
        )
    }
    val nowPlayingList: LiveData<StatefulData<MutableList<Movie>>> = _nowPlayingList

    private val _popularList: MutableLiveData<StatefulData<MutableList<Movie>>> by lazy {
        MutableLiveData(
            StatefulData(
                isLoading = true,
                data = mutableListOf()
            )
        )
    }
    val popularList: LiveData<StatefulData<MutableList<Movie>>> = _popularList

    private val _topRatedList: MutableLiveData<StatefulData<MutableList<Movie>>> by lazy {
        MutableLiveData(
            StatefulData(
                isLoading = true,
                data = mutableListOf()
            )
        )
    }
    val topRatedList: LiveData<StatefulData<MutableList<Movie>>> = _topRatedList

    private val _refreshing = MediatorLiveData<Boolean>().apply {
        addSource(_nowPlayingList) {
            val original = this.value
            val refreshing = it.isLoading || _popularList.value?.isLoading ?: true || _topRatedList.value?.isLoading ?: true
            if (original != refreshing) {
                postValue(refreshing)
            }
        }

        addSource(_popularList) {
            val original = this.value
            val refreshing = it.isLoading || _nowPlayingList.value?.isLoading ?: true || _topRatedList.value?.isLoading ?: true
            if (original != refreshing) {
                postValue(refreshing)
            }
        }

        addSource(_topRatedList) {
            val original = this.value
            val refreshing = it.isLoading || _nowPlayingList.value?.isLoading ?: true || _popularList.value?.isLoading ?: true
            if (original != refreshing) {
                postValue(refreshing)
            }
        }
    }
    val refreshing: LiveData<Boolean> = _refreshing

    fun refresh() {
        _nowPlayingList.postValue(StatefulData(true, mutableListOf()))
        _popularList.postValue(StatefulData(true, mutableListOf()))
        _topRatedList.postValue(StatefulData(true, mutableListOf()))

        viewModelScope.launch(Dispatchers.IO) {
            _nowPlayingList.postValue(
                StatefulData(
                    false,
                    useCase(1, MovieCategory.NowPlaying)?.results?.toMutableList() ?: mutableListOf()
                )
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
            _popularList.postValue(
                StatefulData(
                    false,
                    useCase(1, MovieCategory.Popular)?.results?.toMutableList() ?: mutableListOf()
                )
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
            _topRatedList.postValue(
                StatefulData(
                    false,
                    useCase(1, MovieCategory.TopRated)?.results?.toMutableList() ?: mutableListOf()
                )
            )
        }
    }
}