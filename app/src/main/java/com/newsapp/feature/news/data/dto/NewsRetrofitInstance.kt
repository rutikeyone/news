package com.newsapp.feature.news.data.dto

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsRetrofitInstance {
    companion object {
        private const val BASE_URL = "https://newsapi.org/v2/"
        private val client: OkHttpClient = OkHttpClient.Builder().build()
        fun instance() : Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
        }
    }
}