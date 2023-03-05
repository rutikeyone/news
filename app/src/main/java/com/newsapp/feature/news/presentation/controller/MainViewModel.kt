package com.newsapp.feature.news.presentation.controller

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newsapp.feature.news.domain.entity.CategoryEntity
import com.newsapp.feature.news.domain.repository.GetNewsResult
import com.newsapp.feature.news.domain.repository.NewsRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val newsRepository: NewsRepository,
    private val categories: ArrayList<CategoryEntity>) : ViewModel() {
    var state = MutableLiveData<MainViewState>(MainViewState(categories.first().category, MainViewStatus.Started))
        private set


    fun getNews() {
        viewModelScope.launch {
            state.value = state.value?.copy(status = MainViewStatus.Loading)

            when(val getNewsResult = newsRepository.getNews(state.value!!.selectedCategory)) {
               is GetNewsResult.Failure -> {
                   state.value = state.value?.copy(status = MainViewStatus.Error(getNewsResult.error))
               }
               is GetNewsResult.Success -> {
                   state.value = state.value?.copy(status = MainViewStatus.Data(getNewsResult.news))
               }
           }
        }
    }

    fun changeSelectedCategory(category: CategoryEntity) {
        state.value = state.value!!.copy(selectedCategory = category.category)
        getNews()
    }

}