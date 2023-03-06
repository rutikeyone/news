package com.newsapp.feature.news.domain.repository

import com.newsapp.feature.news.domain.entity.NewsEntity

interface NewsRepository {
    suspend fun getNews(category: String, localeCountry: String) : GetNewsResult
}

sealed class GetNewsResult {

    data class Success(val news: ArrayList<NewsEntity>) : GetNewsResult()

    data class Failure(val error: String?) : GetNewsResult()
}