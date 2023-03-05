package com.newsapp.feature.news.presentation.controller

import com.newsapp.feature.news.domain.entity.NewsEntity

sealed class MainViewState {
    object Started : MainViewState()

    object Loading : MainViewState()

    data class Data(val news: ArrayList<NewsEntity>) : MainViewState()

    data class Error(val message: String?) : MainViewState()
}