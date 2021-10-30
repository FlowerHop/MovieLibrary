package com.flowerhop.movielibrary.domain.model

import com.flowerhop.movielibrary.data.dto.Genre
import com.flowerhop.movielibrary.data.dto.ProductionCompany
import com.flowerhop.movielibrary.data.dto.ProductionCountry

data class MovieDetail(
    val adult: Boolean,
    val backdropPath: String,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    val imdbId: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val productionCompanies: List<ProductionCompany>,
    val productionCountries: List<ProductionCountry>,
    val releaseDate: String,
    val revenue: Int,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
)
