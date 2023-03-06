package com.newsapp.feature.news.presentation.controller

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newsapp.feature.news.domain.entity.CategoryEntity
import com.newsapp.feature.news.domain.entity.LocalizationEntity
import com.newsapp.feature.news.domain.repository.GetNewsResult
import com.newsapp.feature.news.domain.repository.NewsRepository
import kotlinx.coroutines.launch


class MainViewModel(
    private val newsRepository: NewsRepository,
    private val categories: ArrayList<CategoryEntity>,
    private val localeCountry: String
) : ViewModel() {
    var state = MutableLiveData(MainViewState(categories.first().category, localeCountry, MainViewStatus.Started))
        private set


    fun getNews(availableCountry: ArrayList<LocalizationEntity>) {
        viewModelScope.launch {
            state.value = state.value?.copy(status = MainViewStatus.Loading)
            val localeCountry = if(availableCountry.any { it -> it.abbreviation == state.value!!.localeCountry}) state.value!!.localeCountry else "us"

            when(val getNewsResult = newsRepository.getNews(state.value!!.selectedCategory, localeCountry)) {
               is GetNewsResult.Failure -> {
                   state.value = state.value?.copy(status = MainViewStatus.Error(getNewsResult.error))
               }
               is GetNewsResult.Success -> {
                   state.value = state.value?.copy(status = MainViewStatus.Data(getNewsResult.news))
               }
           }
        }
    }

    fun changeSelectedCategory(category: CategoryEntity, availableCountry: ArrayList<LocalizationEntity>) {
        state.value = state.value!!.copy(selectedCategory = category.category)
        getNews(availableCountry)
    }

    fun changeCurrentLocale(localeCountry : String, availableCountry: ArrayList<LocalizationEntity>) {
        state.value = state.value!!.copy(localeCountry = localeCountry)
        getNews(availableCountry)
    }

}