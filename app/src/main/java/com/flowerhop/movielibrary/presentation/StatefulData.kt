package com.flowerhop.movielibrary.presentation

data class StatefulData<T> (
    val isLoading: Boolean = false,
    val data: T,
)