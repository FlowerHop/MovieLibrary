package com.flowerhop.movielibrary.comman

import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import com.flowerhop.movielibrary.R
import com.flowerhop.movielibrary.data.dto.Genre
import com.flowerhop.movielibrary.view.*

object Navigation {
    fun toMovieDetailByAdding(
        supportFragmentManager: FragmentManager,
        fragmentContainerId: Int,
        movieId: Int) {
        supportFragmentManager.beginTransaction().apply {
            add(
                fragmentContainerId,
                MovieDetailFragment::class.java,
                bundleOf(BundleKey.MOVIE_ID to movieId),
                MovieDetailFragment.TAG)
            commit()
        }
    }

    fun toMovieDetailByReplacing(
        supportFragmentManager: FragmentManager,
        fragmentContainerId: Int,
        movieId: Int) {
        supportFragmentManager.beginTransaction().apply {
            replace(
                fragmentContainerId,
                MovieDetailFragment::class.java,
                bundleOf(BundleKey.MOVIE_ID to movieId),
                MovieDetailFragment.TAG)
            addToBackStack(null)
            commit()
        }
    }

    fun toMoviePageByAdding(
        supportFragmentManager: FragmentManager,
        fragmentContainerId: Int,
        categoryInt: Int
    ) {
        supportFragmentManager.beginTransaction().apply {
            add(
                fragmentContainerId,
                MoviePageListFragment::class.java,
                bundleOf(BundleKey.CATEGORY to categoryInt),
                MoviePageListFragment.TAG
            )

            commit()
        }
    }

    fun toMoviePageByReplacing(
        supportFragmentManager: FragmentManager,
        fragmentContainerId: Int,
        genre: Genre
    ) {
        supportFragmentManager.beginTransaction().apply {
            replace(
                fragmentContainerId,
                MoviePageListFragment::class.java, bundleOf(
                    BundleKey.GENRE_ID to genre.id,
                    BundleKey.GENRE_NAME to genre.name
                )
            )
            addToBackStack(null)
            commit()
        }
    }

    fun toSearch(
        supportFragmentManager: FragmentManager,
        fragmentContainerId: Int
    ) {
        supportFragmentManager.beginTransaction().apply {
            add(
                fragmentContainerId,
                SearchingFragment::class.java,
                null,
                SearchingFragment.TAG
            )
            commit()
        }
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