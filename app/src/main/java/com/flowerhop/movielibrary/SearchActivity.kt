package com.flowerhop.movielibrary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.flowerhop.movielibrary.databinding.ActivitySearchBinding
import com.flowerhop.movielibrary.view.SearchingFragment

class SearchActivity: AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolBar.onBackPressed = {
            onBackPressed()
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
}