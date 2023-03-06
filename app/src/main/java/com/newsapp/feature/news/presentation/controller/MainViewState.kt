package com.newsapp.feature.news.presentation.controller

import com.newsapp.feature.news.domain.entity.NewsEntity


data class MainViewState(
    val selectedCategory: String,
    val localeCountry: String,
    val status: MainViewStatus
)

sealed class MainViewStatus {
    object Started : MainViewStatus()

    object Loading : MainViewStatus()

    data class Data(val news: ArrayList<NewsEntity>) : MainViewStatus()

    data class Error(val message: String?) : MainViewStatus()
}