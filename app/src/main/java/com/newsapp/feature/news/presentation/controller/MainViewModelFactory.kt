package com.newsapp.feature.news.presentation.controller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.newsapp.feature.news.domain.entity.CategoryEntity
import com.newsapp.feature.news.domain.repository.NewsRepository

class MainViewModelFactory(
    private val newsRepository: NewsRepository,
    private val categories: ArrayList<CategoryEntity>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(newsRepository, categories) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}