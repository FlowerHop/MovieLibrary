package com.flowerhop.movielibrary

import android.app.Dialog
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.flowerhop.movielibrary.databinding.MovieBottomSheetBinding
import com.flowerhop.movielibrary.network.APIClient
import com.flowerhop.movielibrary.network.entity.Movie
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.SimpleDateFormat
import java.util.*

class MovieBottomSheetDialog(private val movie: Movie): BottomSheetDialogFragment() {
    companion object {
        private const val TAG = "MovieBottomSheetDialog"
        fun show(fragmentManager: FragmentManager, movie: Movie): MovieBottomSheetDialog {
            return MovieBottomSheetDialog(movie).apply {
                this.show(fragmentManager, TAG)
            }
        }

        private fun toReleaseYear(dateStr: String, locale: Locale): Int {
            val calendar = Calendar.getInstance()
            calendar.time = SimpleDateFormat("yyyy", locale).parse(dateStr)
            return calendar.get(Calendar.YEAR)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialog)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            val binding = MovieBottomSheetBinding.inflate(LayoutInflater.from(context))
            setContentView(binding.root)

            Glide.with(binding.root).load("${APIClient.IMAGE_BASE_URL}${movie.posterPath}").into(binding.thumbnail)
            binding.title.text = movie.title
            binding.releaseDate.text = toReleaseYear(movie.releaseDate, getCurrentLocale(resources.configuration)).toString()
            binding.overview.text = movie.overview
            binding.cancel.setOnClickListener { dismiss() }
        }
    }

    private fun getCurrentLocale(configuration: Configuration): Locale {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            configuration.locales[0] else configuration.locale
    }
}