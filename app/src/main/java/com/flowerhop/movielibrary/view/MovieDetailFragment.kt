package com.flowerhop.movielibrary.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.flowerhop.movielibrary.R
import com.flowerhop.movielibrary.comman.Constants
import com.flowerhop.movielibrary.di.Providers
import kotlinx.android.synthetic.main.fragment_movie_detail.*

/**
 * A simple [Fragment] subclass.
 * Use the [MovieDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {
    companion object {
        const val TAG = "MovieDetailFragment"
        const val KEY_ID = "KEY_ID"
    }

    private var movieID: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        enableBackOnActionBar(true)

        arguments?.let {
            movieID = it.getInt(KEY_ID)
        }

        val movieDetailViewModel = Providers.provideMovieDetailViewModel(this, movieID)

        movieDetailViewModel.movieDetail.observe(viewLifecycleOwner) {
            Glide.with(thumbnail).load("${Constants.IMAGE_BASE_URL}${it.posterPath}").into(thumbnail)
            title.text = it.title
            releaseDate.text = "${MovieBottomSheetDialog.toReleaseYear(it.releaseDate, MovieBottomSheetDialog.getCurrentLocale(resources.configuration))}"
            overview.text = it.overview
        }
    }

    override fun onDestroyView() {
        enableBackOnActionBar(false)
        super.onDestroyView()
    }

    private fun enableBackOnActionBar(enable: Boolean) {
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(enable)
            setDisplayShowHomeEnabled(enable)
        }
    }
}