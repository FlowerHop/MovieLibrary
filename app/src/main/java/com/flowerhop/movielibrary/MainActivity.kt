package com.flowerhop.movielibrary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.flowerhop.movielibrary.comman.Navigation
import com.flowerhop.movielibrary.databinding.ActivityMainBinding
import com.flowerhop.movielibrary.view.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Navigation.toHome(supportFragmentManager, R.id.fragmentContainer)

        iconSearch.setOnClickListener {
            startActivity(Intent(this, ToolBarActivity::class.java))
        }
    }
}