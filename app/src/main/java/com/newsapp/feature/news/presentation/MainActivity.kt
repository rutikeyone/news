package com.newsapp.feature.news.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.newsapp.R
import com.newsapp.core.constants.ApiConstants
import com.newsapp.core.constants.BundleConstants
import com.newsapp.databinding.ActivityMainBinding
import com.newsapp.feature.details.Details
import com.newsapp.feature.news.data.dto.NewsDTO
import com.newsapp.feature.news.data.dto.NewsRetrofitInstance
import com.newsapp.feature.news.data.repository.NewsRepositoryImpl
import com.newsapp.feature.news.domain.repository.NewsRepository
import com.newsapp.feature.news.presentation.controller.MainViewModel
import com.newsapp.feature.news.presentation.controller.MainViewModelFactory
import com.newsapp.feature.news.presentation.controller.MainViewState
import com.newsapp.feature.news.presentation.controller.MainViewStatus

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModelFactory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel

    private lateinit var newsDTO: NewsDTO
    private lateinit var newsRepository: NewsRepository


    private lateinit var categoryRecyclerViewAdapter: CategoryRecyclerViewAdapter
    private var newsRecyclerViewAdapter: NewsRecyclerViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        newsDTO = NewsRetrofitInstance.instance().create(NewsDTO::class.java)
        newsRepository = NewsRepositoryImpl(newsDTO)
        viewModelFactory = MainViewModelFactory(newsRepository, ApiConstants.getCategories(applicationContext))
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.getNews()

        categoryRecyclerViewAdapter = CategoryRecyclerViewAdapter(ApiConstants.getCategories(applicationContext)) {
                category -> viewModel.changeSelectedCategory(category)
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.idRVCategories.adapter = categoryRecyclerViewAdapter

        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        viewModel.state.observe(this) {
            when(it.status) {
                is MainViewStatus.Data -> {
                    binding.idLoadingContainerState.visibility = View.GONE
                    binding.idDataContainerState.visibility = View.VISIBLE

                    newsRecyclerViewAdapter = NewsRecyclerViewAdapter(it.status.news) { it ->
                        val intent = Intent(this, Details::class.java)
                        intent.putExtra(BundleConstants.NEWS, it)
                        startActivity(intent)
                    }
                    binding.idRVNews.adapter = newsRecyclerViewAdapter
                }
                is MainViewStatus.Error -> {
                    binding.idLoadingContainerState.visibility = View.GONE
                    binding.idDataContainerState.visibility = View.GONE

                    binding.idErrorContainerState.visibility = View.VISIBLE
                    binding.idTVErrorMessage.text = it.status.message
                    newsRecyclerViewAdapter = null
                }
                MainViewStatus.Loading -> {
                    binding.idLoadingContainerState.visibility = View.VISIBLE
                    binding.idDataContainerState.visibility = View.GONE
                    newsRecyclerViewAdapter = null
                }
                MainViewStatus.Started -> {
                    binding.idLoadingContainerState.visibility = View.GONE
                    binding.idDataContainerState.visibility = View.GONE
                    newsRecyclerViewAdapter = null
                }

            }
        }
    }
}