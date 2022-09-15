package com.flowerhop.movielibrary.presentation.moviedetail

import androidx.lifecycle.*
import com.flowerhop.movielibrary.domain.model.MovieDetail
import com.flowerhop.movielibrary.domain.usecase.FavoriteUseCase
import com.flowerhop.movielibrary.domain.usecase.GetMovieDetailUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val id: Int,
    getMovieDetailUseCase: GetMovieDetailUseCase,
    private val favoriteUseCase: FavoriteUseCase,
) : ViewModel() {
    private val _movieDetail = MediatorLiveData<MovieDetail>().apply {
        viewModelScope.launch(Dispatchers.IO) {
            postValue(getMovieDetailUseCase(id))
        }

        addSource(favoriteUseCase.favoriteMovieIdsLiveData) {
            value = value?.copy(
                myFavorite = it.contains(id)
            )
        }
    }

    val movieDetail: LiveData<MovieDetail?> = _movieDetail

    fun addFavorite() {
        favoriteUseCase.add(id)
    }

    fun removeFavorite() {
        favoriteUseCase.remove(id)
    }
}