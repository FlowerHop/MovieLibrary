package com.flowerhop.movielibrary.comman

object MovieFormatUtil {
    fun toRating(voteAverage: Float?): Float {
        return voteAverage?.div(2) ?: 0f
    }

    fun formatVoteCounts(voteCounts: Int): String {
        if (voteCounts < 1000) return "$voteCounts"
        return "${voteCounts/1000}.${voteCounts%1000}k"
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