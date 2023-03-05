package com.newsapp.core.extension

import com.newsapp.feature.news.data.model.NewsModel
import com.newsapp.feature.news.domain.entity.NewsEntity

fun NewsModel.toEntity() : NewsEntity {
    return  NewsEntity(
        this.author,
        this.title,
        this.description,
        this.url,
        this.urlToImage,
        this.publishedAt,
        this.content,
    )
}