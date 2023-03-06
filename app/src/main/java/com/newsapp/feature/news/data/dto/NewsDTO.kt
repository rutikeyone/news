package com.newsapp.feature.news.data.dto

import com.newsapp.feature.news.data.model.NewsModel
import com.newsapp.feature.news.data.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
interface NewsDTO {
    @GET("top-headlines")
    suspend fun getNews(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String,
        @Query("category") category: String,
    ) : Response<NewsResponse>
}
