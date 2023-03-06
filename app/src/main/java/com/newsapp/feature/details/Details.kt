package com.newsapp.feature.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.newsapp.core.constants.BundleConstants
import com.newsapp.databinding.ActivityDetailsBinding
import com.newsapp.feature.news.domain.entity.NewsEntity
import com.squareup.picasso.Picasso


class Details : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private lateinit var picasso: Picasso
    private lateinit var newsEntity: NewsEntity


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        picasso = Picasso.get()
        newsEntity = this.intent.getParcelableExtra(BundleConstants.NEWS)!!
        picasso.load(newsEntity.urlToImage).into(binding.idIVDetails)

        binding.idTVHeadingDetails.text = newsEntity.title
        binding.idTVDescriptionDetails.text = newsEntity.content
        binding.idBtnReadFullNews.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(newsEntity.url))
            startActivity(intent)
        }

        super.onCreate(savedInstanceState)
        setContentView(binding.root)


    }
}