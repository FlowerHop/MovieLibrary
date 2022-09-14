package com.flowerhop.movielibrary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.flowerhop.movielibrary.databinding.ActivityMovieDetailBinding
import com.flowerhop.movielibrary.view.BundleKey
import com.flowerhop.movielibrary.view.MovieDetailFragment

class MovieDetailActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMovieDetailBinding.inflate(layoutInflater).root)

        val movieId = intent.getIntExtra(BundleKey.MOVIE_ID, -1)
        if (movieId != -1) {
            supportFragmentManager.beginTransaction().apply {
                add(
                    R.id.fragmentContainer,
                    MovieDetailFragment::class.java,
                    bundleOf(BundleKey.MOVIE_ID to movieId),
                    MovieDetailFragment.TAG)
                commit()
            }
            return
        }

        finish()
    }
}