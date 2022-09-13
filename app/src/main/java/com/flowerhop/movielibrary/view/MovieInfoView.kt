package com.flowerhop.movielibrary.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.flowerhop.movielibrary.R
import com.flowerhop.movielibrary.comman.MovieFormatUtil.formatReleaseDate
import com.flowerhop.movielibrary.comman.MovieFormatUtil.formatRuntime
import com.flowerhop.movielibrary.comman.MovieFormatUtil.formatVoteCounts
import com.flowerhop.movielibrary.comman.MovieFormatUtil.toRating
import com.flowerhop.movielibrary.databinding.MovieInfoViewBinding
import com.flowerhop.movielibrary.domain.model.Movie
import com.flowerhop.movielibrary.domain.model.MovieDetail
import kotlinx.android.synthetic.main.movie_info_view.view.*

class MovieInfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr) {
    init {
        val inflater = LayoutInflater.from(context)
        MovieInfoViewBinding.inflate(inflater, this)
        iconFavorite.setOnClickListener { onFavoriteListener?.invoke() }
    }

    var onFavoriteListener: (() -> Unit)? = null

    fun setMovie(movie: Movie) {
        title.text = movie.title
        ratingBar.rating = toRating(movie.voteAverage)
        reviews.text = resources.getString(
            R.string.reviewers,
            formatVoteCounts(movie.voteCount)
        )
        releaseDate.text = formatReleaseDate(movie.releaseDate)
        duration.visibility = View.GONE
        iconFavorite.setChecked(movie.myFavorite)
    }

    fun setMovie(movieDetail: MovieDetail) {
        title.text = movieDetail.title
        ratingBar.rating = toRating(movieDetail.voteAverage.toFloat())
        reviews.text = resources.getString(
            R.string.reviewers,
            formatVoteCounts(movieDetail.voteCount)
        )
        releaseDate.text = formatReleaseDate(movieDetail.releaseDate)
        duration.visibility = View.VISIBLE
        duration.text = formatRuntime(movieDetail.runtime)
        iconFavorite.setChecked(movieDetail.myFavorite)
    }
}