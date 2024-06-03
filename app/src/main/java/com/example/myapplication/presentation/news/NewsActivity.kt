package com.example.myapplication.presentation.news

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsActivity : AppCompatActivity() {
    private val viewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.news_layout)
        findViewById<TextView>(R.id.tvNewsTest).setOnClickListener {
            viewModel.getNewsList()
        }

        viewModel.newsList.observe(this) { response ->
            Log.d("TAG", "onCreate response: ${response}")
        }
    }
}
