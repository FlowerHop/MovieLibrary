package com.flowerhop.movielibrary.comman

import kotlin.math.ln
import kotlin.math.pow

object MovieFormatUtil {
    fun toRating(voteAverage: Float?): Float {
        return voteAverage?.div(2) ?: 0f
    }

    fun formatVoteCounts(voteCounts: Int): String {
        if (voteCounts < 1000) return "$voteCounts"
        val exp = (ln(voteCounts.toDouble()) / ln(1000.0)).toInt()
        return String.format(
            "%.1f%c",
            voteCounts / 1000.0.pow(exp.toDouble()),
            "kMGTPE"[exp - 1]
        )
    }

    fun formatRuntime(timeInMinute: Int): String {
        val hour = timeInMinute/60
        val minute = timeInMinute%60
        if (hour == 0)
            return "${minute}m"

        return "${hour}h ${minute}m"
    }

    fun formatReleaseDate(releaseDate: String): String {
        return releaseDate.replace("-", "/")
    }
}