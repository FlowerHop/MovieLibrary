package com.flowerhop.movielibrary.di

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.flowerhop.movielibrary.AnyViewModelFactory
import com.flowerhop.movielibrary.data.remote.TMDBApi
import com.flowerhop.movielibrary.data.repository.TMDBRepositoryImpl
import com.flowerhop.movielibrary.domain.repository.TMDBRepository
import com.flowerhop.movielibrary.domain.usecase.GetCategoryListUseCase
import com.flowerhop.movielibrary.network.APIClient
import com.flowerhop.movielibrary.presentation.categorylist.MovieCategoryViewModel
import com.flowerhop.movielibrary.view.MovieCategory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Providers {
    fun provideTMDBApi(): TMDBApi {
        val okHttp = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(APIClient.BASE_URL)
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(TMDBApi::class.java)
    }

    fun provideTMDBRepository(tmdbApi: TMDBApi): TMDBRepository {
        return TMDBRepositoryImpl(tmdbApi)
    }

    fun provideGetCategoryListUseCase(tmdbRepository: TMDBRepository): GetCategoryListUseCase {
        return GetCategoryListUseCase(tmdbRepository)
    }

    fun provideMovieCategoryViewModel(viewModelStoreOwner: ViewModelStoreOwner, category: MovieCategory): MovieCategoryViewModel {
        val tmdbApi = provideTMDBApi()
        val tmdbRepository: TMDBRepository = provideTMDBRepository(tmdbApi)
        val useCase = provideGetCategoryListUseCase(tmdbRepository)
        val viewModelFactory = AnyViewModelFactory {
            MovieCategoryViewModel(
                category = category,
                useCase = useCase
            )
        }

        return ViewModelProvider(viewModelStoreOwner, viewModelFactory).get(MovieCategoryViewModel::class.java)
    }
}