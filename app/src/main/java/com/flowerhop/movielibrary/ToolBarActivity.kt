package com.flowerhop.movielibrary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.flowerhop.movielibrary.databinding.ActivityToolBarBinding
import com.flowerhop.movielibrary.view.BundleKey.CATEGORY
import com.flowerhop.movielibrary.view.BundleKey.MOVIE_ID
import com.flowerhop.movielibrary.view.MoviePageListFragment
import com.flowerhop.movielibrary.view.MovieDetailFragment
import com.flowerhop.movielibrary.view.SearchingFragment
import kotlinx.android.synthetic.main.activity_tool_bar.*

class ToolBarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityToolBarBinding.inflate(layoutInflater).root)
        setSupportActionBar(toolBar)

        val movieId = intent.getIntExtra(MOVIE_ID, -1)
        if (movieId != -1) {
            navigateToMovieDetail(movieId)
            return
        }

        val categoryInt = intent.getIntExtra(CATEGORY, -1)
        if (categoryInt != -1) {
            navigateToMoviePage(categoryInt)
            return
        }

        navigateToSearching()
    }

    private fun navigateToMovieDetail(movieId: Int) {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        supportFragmentManager.beginTransaction().apply {
            add(R.id.fragmentContainer,
                MovieDetailFragment::class.java,
                bundleOf(MOVIE_ID to movieId),
                MovieDetailFragment.TAG)
            commit()
        }
    }

    private fun navigateToMoviePage(categoryInt: Int) {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        supportFragmentManager.beginTransaction().apply {
            add(
                R.id.fragmentContainer,
                MoviePageListFragment::class.java,
                bundleOf(CATEGORY to categoryInt),
                MoviePageListFragment.TAG
            )

            commit()
        }
    }

    private fun navigateToSearching() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(false)
        }

        supportFragmentManager.beginTransaction().apply {
            add(
                R.id.fragmentContainer,
                SearchingFragment::class.java,
                null,
                SearchingFragment.TAG
            )
            commit()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}