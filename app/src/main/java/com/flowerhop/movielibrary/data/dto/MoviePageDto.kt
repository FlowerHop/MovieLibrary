package com.flowerhop.movielibrary.data.dto


import com.flowerhop.movielibrary.domain.model.MoviePage
import com.google.gson.annotations.SerializedName

data class MoviePageDto(
    @SerializedName("dates")
    val dates: Dates,
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)

fun MoviePageDto.toMoviePage(): MoviePage {
    return MoviePage(
        dates = dates.copy(),
        page = page,
        results = results.map { it.toMovie() },
        totalPages = totalPages,
        totalResults = totalResults
    )
}