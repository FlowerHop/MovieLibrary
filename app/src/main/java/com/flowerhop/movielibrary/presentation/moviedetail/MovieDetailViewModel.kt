package com.flowerhop.movielibrary.presentation.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flowerhop.movielibrary.domain.model.MovieDetail
import com.flowerhop.movielibrary.domain.usecase.GetMovieDetailUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailViewModel(id: Int, useCase: GetMovieDetailUseCase): ViewModel() {
    private val _movieDetail = MutableLiveData<MovieDetail>().apply {
        viewModelScope.launch(Dispatchers.IO) {
            postValue(useCase(id))
        }
    }

    val movieDetail: LiveData<MovieDetail> = _movieDetail
}