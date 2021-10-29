package com.flowerhop.movielibrary.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.flowerhop.movielibrary.R
import com.flowerhop.movielibrary.comman.Constants
import com.flowerhop.movielibrary.comman.Constants.BUNDLE_KEY_MOVIE_ID
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
    }

    private var movieID: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            movieID = it.getInt(BUNDLE_KEY_MOVIE_ID)
        }
        shimmerHolder.startShimmer()
        val movieDetailViewModel = Providers.provideMovieDetailViewModel(this, movieID)

        movieDetailViewModel.movieDetail.observe(viewLifecycleOwner) {
            Glide.with(thumbnail).load("${Constants.IMAGE_BASE_URL}${it.backdropPath}").listener(object : RequestListener<Drawable> {
                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    shimmerHolder.visibility = View.GONE
                    movieInfo.visibility = View.VISIBLE
                    overview.text = it.overview
                    divider.visibility = View.VISIBLE
                    genreChips.visibility = View.VISIBLE
                    shimmerHolder.stopShimmer()
                    return false
                }

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    shimmerHolder.visibility = View.GONE
                    movieInfo.visibility = View.VISIBLE
                    overview.text = it.overview
                    divider.visibility = View.VISIBLE
                    genreChips.visibility = View.VISIBLE
                    shimmerHolder.stopShimmer()
                    return false
                }
            }).into(thumbnail)
            movieInfo.setMovie(it)
            it.genres.map { genre ->
                val ctx: Context = context ?: return@map
                genreChips.addView(
                    UiUtil.createGenreChip(ctx, genre.name)
                )
            }
        }
    }
}