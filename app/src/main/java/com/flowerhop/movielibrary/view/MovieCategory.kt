package com.flowerhop.movielibrary.view

enum class MovieCategory(name: String, val path: String) {
    NowPlaying("Now Playing", "now_playing"),
    Popular("Popular", "popular"),
    TopRated("Top Rated", "top_rated");

    override fun toString(): String {
        return name
    }
}