package com.flowerhop.movielibrary

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.flowerhop.movielibrary.data.dto.Genre
import com.flowerhop.movielibrary.databinding.ActivityMovieListBinding
import com.flowerhop.movielibrary.domain.model.MovieCategory
import com.flowerhop.movielibrary.view.BundleKey
import com.flowerhop.movielibrary.view.MoviePageListFragment

class MovieListActivity : AppCompatActivity() {
    companion object {
        fun startCategoryMovieList(
            activity: Activity,
            categoryInt: Int
        ) {
            activity.startActivity(Intent().apply {
                setClass(activity, MovieListActivity::class.java)
                putExtra(BundleKey.CATEGORY, categoryInt)
            })
        }

        fun startGenreMovieList(
            activity: Activity,
            genre: Genre
        ) {
            activity.startActivity(Intent().apply {
                setClass(activity, MovieListActivity::class.java)
                putExtra(BundleKey.GENRE_ID, genre.id)
                putExtra(BundleKey.GENRE_NAME, genre.name)
            })
        }
    }

    private lateinit var binding: ActivityMovieListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolBar.onBackPressed = {
            this@MovieListActivity.onBackPressed()
        }

        val categoryInt = intent.getIntExtra(BundleKey.CATEGORY, -1)
        if (categoryInt != -1) {
            val category = MovieCategory.values()[categoryInt]
            binding.toolBar.setTitle("$category")
            supportFragmentManager.beginTransaction().apply {
                add(
                    R.id.fragmentContainer,
                    MoviePageListFragment::class.java,
                    bundleOf(BundleKey.CATEGORY to categoryInt),
                    MoviePageListFragment.TAG
                )

                commit()
            }
            return
        }


        val genreId = intent.getIntExtra(BundleKey.GENRE_ID, -1)
        val genreName = intent.getStringExtra(BundleKey.GENRE_NAME) ?: ""

        if (genreId != -1) {
            binding.toolBar.setTitle(genreName)
            supportFragmentManager.beginTransaction().apply {
                add(
                    R.id.fragmentContainer,
                    MoviePageListFragment::class.java, bundleOf(
                        BundleKey.GENRE_ID to genreId,
                        BundleKey.GENRE_NAME to genreName
                    )
                )
                commit()
            }

            return
        }

        // if invalid input
        finish()
    }
}