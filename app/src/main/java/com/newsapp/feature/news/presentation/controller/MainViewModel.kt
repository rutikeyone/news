package com.newsapp.feature.news.presentation.controller

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newsapp.feature.news.domain.repository.GetNewsResult
import com.newsapp.feature.news.domain.repository.NewsRepository
import kotlinx.coroutines.launch

class MainViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    var state = MutableLiveData<MainViewState>(MainViewState.Started)
        private set


    fun getNews(category: String) {
        viewModelScope.launch {
            state.value = MainViewState.Loading

            when(val getNewsResult = newsRepository.getNews(category)) {
               is GetNewsResult.Failure -> {
                   state.value = MainViewState.Error(getNewsResult.error)
               }
               is GetNewsResult.Success -> {
                   state.value = MainViewState.Data(getNewsResult.news)
               }
           }
        }
    }

}