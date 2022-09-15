package com.flowerhop.movielibrary.di

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.flowerhop.movielibrary.AnyViewModelFactory
import com.flowerhop.movielibrary.MovieLibraryApplication
import com.flowerhop.movielibrary.comman.Constants
import com.flowerhop.movielibrary.data.dto.Genre
import com.flowerhop.movielibrary.data.remote.TMDBApi
import com.flowerhop.movielibrary.data.repository.MyFavoritesRepositoryImpl
import com.flowerhop.movielibrary.data.repository.TMDBRepositoryImpl
import com.flowerhop.movielibrary.domain.repository.TMDBRepository
import com.flowerhop.movielibrary.presentation.MoviesViewModel
import com.flowerhop.movielibrary.presentation.pagelist.MovieCategoryViewModel
import com.flowerhop.movielibrary.presentation.moviedetail.MovieDetailViewModel
import com.flowerhop.movielibrary.domain.model.MovieCategory
import com.flowerhop.movielibrary.domain.repository.MyFavoritesRepository
import com.flowerhop.movielibrary.domain.usecase.*
import com.flowerhop.movielibrary.presentation.pagelist.MovieGenreViewModel
import com.flowerhop.movielibrary.presentation.pagelist.MovieSearchingViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

object Providers {
    val tmdbApi by lazy {
        provideTMDBApi()
    }

    val tmdbRepository by lazy {
        provideTMDBRepository(tmdbApi)
    }

    val favoritesRepository by lazy {
        provideFavoritesRepository(tmdbRepository, File(MovieLibraryApplication.getInstance().cacheDir, "my_fav"))
    }

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

    fun provideFavoritesRepository(tmdbRepository: TMDBRepository, cacheFile: File? = null) : MyFavoritesRepository {
        return MyFavoritesRepositoryImpl(tmdbRepository, cacheFile)
    }

    fun provideGetCategoryListUseCase(): GetCategoryListUseCase {
        return GetCategoryListUseCase(tmdbRepository, favoritesRepository)
    }

    fun provideGetMovieDetailUseCase(): GetMovieDetailUseCase {
        return GetMovieDetailUseCase(tmdbRepository, favoritesRepository)
    }

    fun provideFavoriteUseCase(): FavoriteUseCase {
        return FavoriteUseCase(favoritesRepository)
    }

    fun provideSearchAtPageUseCase(): SearchAtPageUseCase {
        return SearchAtPageUseCase(tmdbRepository, favoritesRepository)
    }

    fun provideDiscoverMoviesWithGenresUseCase(): DiscoverMoviesWithGenres {
        return DiscoverMoviesWithGenres(tmdbRepository, favoritesRepository)
    }

    fun provideMovieCategoryViewModel(viewModelStoreOwner: ViewModelStoreOwner, category: MovieCategory): MovieCategoryViewModel {
        val getCategoryListUseCase = provideGetCategoryListUseCase()
        val favoriteUseCase = provideFavoriteUseCase()
        val viewModelFactory = AnyViewModelFactory {
            MovieCategoryViewModel(
                category = category,
                getCategoryListUseCase = getCategoryListUseCase,
                favoriteUseCase = favoriteUseCase
            )
        }

        return ViewModelProvider(viewModelStoreOwner, viewModelFactory).get(MovieCategoryViewModel::class.java)
    }

    fun provideMovieGenreViewModel(viewModelStoreOwner: ViewModelStoreOwner, genre: Genre): MovieGenreViewModel {
        val discoverMoviesWithGenres = provideDiscoverMoviesWithGenresUseCase()
        val favoriteUseCase = provideFavoriteUseCase()
        val viewModelFactory = AnyViewModelFactory {
            MovieGenreViewModel(
                genre = genre,
                discoverMoviesWithGenres = discoverMoviesWithGenres,
                favoriteUseCase = favoriteUseCase
            )
        }

        return ViewModelProvider(viewModelStoreOwner, viewModelFactory).get(MovieGenreViewModel::class.java)
    }

    fun provideMovieDetailViewModel(viewModelStoreOwner: ViewModelStoreOwner, movieId: Int): MovieDetailViewModel{
        val getMovieDetailUseCase = provideGetMovieDetailUseCase()
        val favoriteUseCase = provideFavoriteUseCase()
        val viewModelFactory = AnyViewModelFactory {
            MovieDetailViewModel(
                id = movieId,
                getMovieDetailUseCase = getMovieDetailUseCase,
                favoriteUseCase = favoriteUseCase
            )
        }

        return ViewModelProvider(viewModelStoreOwner, viewModelFactory).get(MovieDetailViewModel::class.java)
    }

    fun provideMoviesViewModel(viewModelStoreOwner: ViewModelStoreOwner): MoviesViewModel {
        val useCase = provideGetCategoryListUseCase()
        val viewModelFactory = AnyViewModelFactory {
            MoviesViewModel(
                useCase = useCase
            )
        }

        return ViewModelProvider(viewModelStoreOwner, viewModelFactory).get(MoviesViewModel::class.java)
    }

    fun provideMovieSearchingViewModel(viewModelStoreOwner: ViewModelStoreOwner): MovieSearchingViewModel {
        val searchAtPageUseCase = provideSearchAtPageUseCase()
        val getCategoryListUseCase = provideGetCategoryListUseCase()
        val viewModelFactory = AnyViewModelFactory {
            MovieSearchingViewModel(
                searchAtPageUseCase = searchAtPageUseCase,
                getCategoryListUseCase = getCategoryListUseCase,
                myFavoritesRepository = favoritesRepository
            )
        }

        return ViewModelProvider(viewModelStoreOwner, viewModelFactory).get(MovieSearchingViewModel::class.java)
    }
}