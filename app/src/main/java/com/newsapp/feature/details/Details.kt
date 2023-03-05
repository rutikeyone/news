package com.newsapp.feature.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.newsapp.R
import com.newsapp.core.constants.BundleConstants
import com.newsapp.feature.news.domain.entity.NewsEntity

class Details : AppCompatActivity() {
    private lateinit var newsEntity: NewsEntity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        newsEntity = this.intent.getParcelableExtra<NewsEntity>(BundleConstants.NEWS)!!
        Log.i("MYLOG", newsEntity.toString())
    }
}