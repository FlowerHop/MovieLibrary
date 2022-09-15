package com.flowerhop.movielibrary.comman

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import com.flowerhop.movielibrary.MovieDetailActivity
import com.flowerhop.movielibrary.MovieListActivity
import com.flowerhop.movielibrary.R
import com.flowerhop.movielibrary.SearchActivity
import com.flowerhop.movielibrary.data.dto.Genre
import com.flowerhop.movielibrary.view.*

object Navigation {
    fun toSearch(activity: Activity) {
        val intent = Intent(activity, SearchActivity::class.java)
        activity.startActivity(intent)
    }
}