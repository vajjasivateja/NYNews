package com.example.app.nynews.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.app.nynews.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.llSearchArticle.setOnClickListener {
            val searchArticlesActivity = Intent(this@MainActivity, SearchArticlesActivity::class.java)
            searchArticlesActivity.putExtra("SEARCH", "search")
            startActivity(searchArticlesActivity)
        }

        binding.llMostViewed.setOnClickListener {
            val searchArticlesActivity = Intent(this@MainActivity, SearchArticlesActivity::class.java)
            searchArticlesActivity.putExtra("POPULAR_VIEWED", "viewed")
            startActivity(searchArticlesActivity)
        }

        binding.llMostEmailed.setOnClickListener {
            val searchArticlesActivity = Intent(this@MainActivity, SearchArticlesActivity::class.java)
            searchArticlesActivity.putExtra("POPULAR_EMAILED", "emailed")
            startActivity(searchArticlesActivity)
        }

        binding.llMostShared.setOnClickListener {
            val searchArticlesActivity = Intent(this@MainActivity, SearchArticlesActivity::class.java)
            searchArticlesActivity.putExtra("POPULAR_SHARED", "shared")
            startActivity(searchArticlesActivity)
        }
    }
}