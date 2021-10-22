package com.flowerhop.movielibrary.domain.model

import com.flowerhop.movielibrary.data.dto.Dates

data class MoviePage(
    val dates: Dates,
    val page: Int,
    val results: List<Movie>,
    val totalPages: Int,
    val totalResults: Int
)
