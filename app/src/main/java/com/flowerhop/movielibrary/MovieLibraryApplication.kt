package com.flowerhop.movielibrary

import android.app.Application

class MovieLibraryApplication: Application() {
    companion object {
        private var INSTANCE: MovieLibraryApplication? = null

        fun getInstance(): MovieLibraryApplication = INSTANCE!!
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}