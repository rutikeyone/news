package com.newsapp.feature.news.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.newsapp.R
import com.newsapp.feature.news.domain.entity.NewsEntity
import com.squareup.picasso.Picasso

class NewsRecyclerViewAdapter(private val news: ArrayList<NewsEntity>) : RecyclerView.Adapter<NewsViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val tile = layoutInflater.inflate(R.layout.news_recycler_view_tile, parent, false)
        return NewsViewHolder(tile)
    }

    override fun getItemCount(): Int {
        return news.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(news[position])
    }

}

class NewsViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun bind(news: NewsEntity) {
        val picasso = Picasso.get()
        val newsImage = view.findViewById<ImageView>(R.id.idIVNews)
        val newsHeading = view.findViewById<TextView>(R.id.idTVHead)
        val newsSubTitle = view.findViewById<TextView>(R.id.idTVSubTitle)

        picasso.load(news.urlToImage).into(newsImage)
        newsHeading.text = news.title

        if(!news.description.isNullOrEmpty()) {
            newsSubTitle.text = news.description
        } else {
            newsSubTitle.visibility = View.GONE
        }

    }
}