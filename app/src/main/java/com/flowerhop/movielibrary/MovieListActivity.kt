package com.flowerhop.movielibrary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.flowerhop.movielibrary.databinding.ActivityMovieListBinding
import com.flowerhop.movielibrary.domain.model.MovieCategory
import com.flowerhop.movielibrary.view.BundleKey
import com.flowerhop.movielibrary.view.MoviePageListFragment

class MovieListActivity : AppCompatActivity() {
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