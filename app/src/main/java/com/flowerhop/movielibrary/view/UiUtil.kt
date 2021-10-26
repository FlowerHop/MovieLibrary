package com.flowerhop.movielibrary.view

import android.content.Context
import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import com.flowerhop.movielibrary.R
import com.google.android.material.chip.Chip

object UiUtil {
    fun createGenreChip(context: Context, genreStr: String) = Chip(context).apply {
        text = genreStr
        setTextColor(ContextCompat.getColor(context, R.color.on_primary))
        chipBackgroundColor = ColorStateList.valueOf(
            ContextCompat.getColor(
                context,
                R.color.primary
            )
        )
    }
}