package com.flowerhop.movielibrary.comman

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import com.flowerhop.movielibrary.MovieDetailActivity
import com.flowerhop.movielibrary.MovieListActivity
import com.flowerhop.movielibrary.R
import com.flowerhop.movielibrary.SearchActivity
import com.flowerhop.movielibrary.data.dto.Genre
import com.flowerhop.movielibrary.view.*

object Navigation {
    fun toMovieDetailActivity(
        activity: Activity,
        movieId: Int
    ) {
        activity.startActivity(Intent().apply {
            setClass(activity, MovieDetailActivity::class.java)
            putExtra(BundleKey.MOVIE_ID, movieId)
        })
    }

    fun toMovieListActivity(
        activity: Activity,
        categoryInt: Int
    ) {
        activity.startActivity(Intent().apply {
            setClass(activity, MovieListActivity::class.java)
            putExtra(BundleKey.CATEGORY, categoryInt)
        })
    }

    fun toMovieListActivity(
        activity: Activity,
        genre: Genre
    ) {
        activity.startActivity(Intent().apply {
            setClass(activity, MovieListActivity::class.java)
            putExtra(BundleKey.GENRE_ID, genre.id)
            putExtra(BundleKey.GENRE_NAME, genre.name)
        })
    }

    fun toSearch(activity: Activity) {
        val intent = Intent(activity, SearchActivity::class.java)
        activity.startActivity(intent)
    }

    fun toHome(
        supportFragmentManager: FragmentManager,
        fragmentContainerId: Int
    ) {
        supportFragmentManager.beginTransaction().apply {
            add(
                fragmentContainerId,
                HomeFragment::class.java,
                null,
                HomeFragment.TAG
            )
            commit()
        }
    }
}