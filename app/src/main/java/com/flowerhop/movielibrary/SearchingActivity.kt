package com.flowerhop.movielibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.flowerhop.movielibrary.view.SearchingFragment
import kotlinx.android.synthetic.main.activity_category.*

class SearchingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        setSupportActionBar(toolBar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
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