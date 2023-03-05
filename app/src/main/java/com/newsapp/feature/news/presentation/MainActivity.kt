package com.newsapp.feature.news.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.newsapp.R
import com.newsapp.core.constants.ApiConstants
import com.newsapp.databinding.ActivityMainBinding
import com.newsapp.feature.news.data.dto.NewsDTO
import com.newsapp.feature.news.data.dto.NewsRetrofitInstance
import com.newsapp.feature.news.data.repository.NewsRepositoryImpl
import com.newsapp.feature.news.domain.repository.NewsRepository
import com.newsapp.feature.news.presentation.controller.MainViewModel
import com.newsapp.feature.news.presentation.controller.MainViewModelFactory
import com.newsapp.feature.news.presentation.controller.MainViewState

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModelFactory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel

    private lateinit var newsDTO: NewsDTO
    private lateinit var newsRepository: NewsRepository


    private var categoryRecyclerViewAdapter: CategoryRecyclerViewAdapter? = null
    private var newsRecyclerViewAdapter: NewsRecyclerViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)

        newsDTO = NewsRetrofitInstance.instance().create(NewsDTO::class.java)
        newsRepository = NewsRepositoryImpl(newsDTO)
        viewModelFactory = MainViewModelFactory(newsRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.getNews(ApiConstants.getCategories(applicationContext).first().category)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        viewModel.state.observe(this) {
            when(it) {
                is MainViewState.Data -> {
                    binding.idLoadingContainerState.visibility = View.GONE
                    binding.idDataContainerState.visibility = View.VISIBLE

                    categoryRecyclerViewAdapter = CategoryRecyclerViewAdapter(ApiConstants.getCategories(applicationContext))
                    newsRecyclerViewAdapter = NewsRecyclerViewAdapter(it.news)
                    binding.idRVCategories.adapter = categoryRecyclerViewAdapter
                    binding.idRVNews.adapter = newsRecyclerViewAdapter
                }
                is MainViewState.Error -> {
                    binding.idLoadingContainerState.visibility = View.GONE
                    binding.idDataContainerState.visibility = View.GONE

                    binding.idErrorContainerState.visibility = View.VISIBLE
                    binding.idTVErrorMessage.text = it.message

                    categoryRecyclerViewAdapter = null
                    newsRecyclerViewAdapter = null
                }
                MainViewState.Loading -> {
                    binding.idLoadingContainerState.visibility = View.VISIBLE
                    binding.idDataContainerState.visibility = View.GONE

                    categoryRecyclerViewAdapter = null
                    newsRecyclerViewAdapter = null
                }
                MainViewState.Started -> {
                    binding.idLoadingContainerState.visibility = View.GONE
                    binding.idDataContainerState.visibility = View.GONE

                    categoryRecyclerViewAdapter = null
                    newsRecyclerViewAdapter = null
                }
            }
        }
    }
}