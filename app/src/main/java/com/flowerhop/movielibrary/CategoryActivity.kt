package com.flowerhop.movielibrary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.flowerhop.movielibrary.comman.Constants.BUNDLE_KEY_CATEGORY
import com.flowerhop.movielibrary.comman.Constants.BUNDLE_KEY_MOVIE_ID
import com.flowerhop.movielibrary.databinding.ActivityCategoryBinding
import com.flowerhop.movielibrary.view.MoviePageListFragment
import com.flowerhop.movielibrary.view.MovieDetailFragment
import kotlinx.android.synthetic.main.activity_category.*

class CategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityCategoryBinding.inflate(layoutInflater).root)
        setSupportActionBar(toolBar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        val movieId = intent.getIntExtra(BUNDLE_KEY_MOVIE_ID, -1)
        if (movieId != -1) {
            supportFragmentManager.beginTransaction().apply {
                add(R.id.fragmentContainer, MovieDetailFragment::class.java,
                    bundleOf(BUNDLE_KEY_MOVIE_ID to movieId),
                    MovieDetailFragment.TAG)
                commit()
            }
            return
        }

        intent.getIntExtra(BUNDLE_KEY_CATEGORY, 0).let {
            supportFragmentManager.beginTransaction().apply {
                add(
                    R.id.fragmentContainer,
                    MoviePageListFragment::class.java,
                    bundleOf(BUNDLE_KEY_CATEGORY to it),
                    MoviePageListFragment.TAG
                )

                commit()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}