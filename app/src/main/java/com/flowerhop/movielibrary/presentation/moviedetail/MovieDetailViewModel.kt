package com.flowerhop.movielibrary.presentation.moviedetail

import androidx.lifecycle.*
import com.flowerhop.movielibrary.domain.model.MovieDetail
import com.flowerhop.movielibrary.domain.repository.MyFavoritesRepository
import com.flowerhop.movielibrary.domain.usecase.GetMovieDetailUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MovieDetailViewModel(private val id: Int, useCase: GetMovieDetailUseCase, private val myFavoritesRepository: MyFavoritesRepository): ViewModel() {
    private val _movieDetail = MediatorLiveData<MovieDetail>().apply {
        viewModelScope.launch(Dispatchers.IO) {
            postValue(useCase(id))
        }

        addSource(myFavoritesRepository.getIdListLiveData()) {
            value = value?.copy(
                myFavorite = it.contains(id)
            )
        }
    }

    val movieDetail: LiveData<MovieDetail?> = _movieDetail

    fun addFavorite() {
        myFavoritesRepository.add(id)
    }

    fun removeFavorite() {
        myFavoritesRepository.remove(id)
    }
}