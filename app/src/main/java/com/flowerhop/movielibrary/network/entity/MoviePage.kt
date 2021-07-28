package com.flowerhop.movielibrary.network.entity

import com.google.gson.annotations.SerializedName

data class MoviePage(
    @SerializedName("dates") val dates: Dates,
    @SerializedName("page") val page: Int,
    @SerializedName("results") val movies: List<Movie>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)