package com.flowerhop.movielibrary.di

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.flowerhop.movielibrary.AnyViewModelFactory
import com.flowerhop.movielibrary.comman.Constants
import com.flowerhop.movielibrary.data.dto.Genre
import com.flowerhop.movielibrary.data.remote.TMDBApi
import com.flowerhop.movielibrary.data.repository.TMDBRepositoryImpl
import com.flowerhop.movielibrary.domain.repository.TMDBRepository
import com.flowerhop.movielibrary.domain.usecase.GetCategoryListUseCase
import com.flowerhop.movielibrary.domain.usecase.GetMovieDetailUseCase
import com.flowerhop.movielibrary.presentation.MoviesViewModel
import com.flowerhop.movielibrary.presentation.pagelist.MovieCategoryViewModel
import com.flowerhop.movielibrary.presentation.moviedetail.MovieDetailViewModel
import com.flowerhop.movielibrary.domain.model.MovieCategory
import com.flowerhop.movielibrary.domain.usecase.DiscoverMoviesWithGenres
import com.flowerhop.movielibrary.domain.usecase.SearchAtPageUseCase
import com.flowerhop.movielibrary.presentation.pagelist.MovieGenreViewModel
import com.flowerhop.movielibrary.presentation.pagelist.MovieSearchingViewModel
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
            .baseUrl(Constants.BASE_URL)
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

    fun provideGetMovieDetailUseCase(tmdbRepository: TMDBRepository): GetMovieDetailUseCase {
        return GetMovieDetailUseCase(tmdbRepository)
    }

    fun provideSearchAtPageUseCase(tmdbRepository: TMDBRepository): SearchAtPageUseCase {
        return SearchAtPageUseCase(tmdbRepository)
    }

    fun provideDiscoverMoviesWithGenresUseCase(tmdbRepository: TMDBRepository): DiscoverMoviesWithGenres {
        return DiscoverMoviesWithGenres(tmdbRepository)
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

    fun provideMovieGenreViewModel(viewModelStoreOwner: ViewModelStoreOwner, genre: Genre): MovieGenreViewModel {
        val tmdbApi = provideTMDBApi()
        val tmdbRepository: TMDBRepository = provideTMDBRepository(tmdbApi)
        val useCase = provideDiscoverMoviesWithGenresUseCase(tmdbRepository)
        val viewModelFactory = AnyViewModelFactory {
            MovieGenreViewModel(
                genre = genre,
                useCase = useCase
            )
        }

        return ViewModelProvider(viewModelStoreOwner, viewModelFactory).get(MovieGenreViewModel::class.java)
    }

    fun provideMovieDetailViewModel(viewModelStoreOwner: ViewModelStoreOwner, movieId: Int): MovieDetailViewModel{
        val tmdbApi = provideTMDBApi()
        val tmdbRepository: TMDBRepository = provideTMDBRepository(tmdbApi)
        val useCase = provideGetMovieDetailUseCase(tmdbRepository)
        val viewModelFactory = AnyViewModelFactory {
            MovieDetailViewModel(
                id = movieId,
                useCase = useCase
            )
        }

        return ViewModelProvider(viewModelStoreOwner, viewModelFactory).get(MovieDetailViewModel::class.java)
    }

    fun provideMoviesViewModel(viewModelStoreOwner: ViewModelStoreOwner): MoviesViewModel {
        val tmdbApi = provideTMDBApi()
        val tmdbRepository: TMDBRepository = provideTMDBRepository(tmdbApi)
        val useCase = provideGetCategoryListUseCase(tmdbRepository)
        val viewModelFactory = AnyViewModelFactory {
            MoviesViewModel(
                useCase = useCase
            )
        }

        return ViewModelProvider(viewModelStoreOwner, viewModelFactory).get(MoviesViewModel::class.java)
    }

    fun provideMovieSearchingViewModel(viewModelStoreOwner: ViewModelStoreOwner): MovieSearchingViewModel {
        val tmdbApi = provideTMDBApi()
        val tmdbRepository: TMDBRepository = provideTMDBRepository(tmdbApi)
        val searchAtPageUseCase = provideSearchAtPageUseCase(tmdbRepository)
        val getCategoryListUseCase = provideGetCategoryListUseCase(tmdbRepository)
        val viewModelFactory = AnyViewModelFactory {
            MovieSearchingViewModel(
                searchAtPageUseCase = searchAtPageUseCase,
                getCategoryListUseCase = getCategoryListUseCase
            )
        }

        return ViewModelProvider(viewModelStoreOwner, viewModelFactory).get(MovieSearchingViewModel::class.java)
    }
}