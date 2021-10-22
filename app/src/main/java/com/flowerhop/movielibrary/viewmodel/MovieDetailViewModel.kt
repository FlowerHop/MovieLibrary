package com.flowerhop.movielibrary.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
//import com.flowerhop.movielibrary.domain.model.MovieDetail
import com.flowerhop.movielibrary.domain.usecase.GetMovieDetailUseCase
import com.flowerhop.movielibrary.network.entity.MovieDetail
import com.flowerhop.movielibrary.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailViewModel(id: Int, repo: MovieRepository): ViewModel() {
    var movieDetail = MutableLiveData<MovieDetail>().apply {
        viewModelScope.launch(Dispatchers.IO) {
            postValue(repo.getMovie(id))
        }
    }
}
//
//class MovieDetailViewModel(id: Int, useCase: GetMovieDetailUseCase): ViewModel() {
//    var movieDetail = MutableLiveData<MovieDetail>().apply {
//        viewModelScope.launch(Dispatchers.IO) {
//            postValue(useCase(id))
//        }
//    }
//}