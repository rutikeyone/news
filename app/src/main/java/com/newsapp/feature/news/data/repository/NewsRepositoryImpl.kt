package com.newsapp.feature.news.data.repository

import com.newsapp.core.constants.ApiConstants
import com.newsapp.core.extension.toEntity
import com.newsapp.feature.news.data.dto.NewsDTO
import com.newsapp.feature.news.domain.entity.NewsEntity
import com.newsapp.feature.news.domain.repository.GetNewsResult
import com.newsapp.feature.news.domain.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class NewsRepositoryImpl(private val newsDTO: NewsDTO) : NewsRepository {
    override suspend fun getNews(category: String, localeCountry:String): GetNewsResult = withContext(Dispatchers.Default) {
      try {
          val response = newsDTO.getNews(apiKey = ApiConstants.API_KEY, category = category, country = localeCountry)
          val entities =  ArrayList(response.body().let {
              it?.articles?.map { it -> it.toEntity() }
          })
          return@withContext GetNewsResult.Success(entities)
      } catch (e: Exception) {
          return@withContext GetNewsResult.Failure(e.message)
      }
    }
}