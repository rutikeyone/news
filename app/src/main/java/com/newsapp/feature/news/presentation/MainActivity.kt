package com.newsapp.feature.news.presentation

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.newsapp.R
import com.newsapp.core.constants.ApiConstants
import com.newsapp.core.constants.BundleConstants
import com.newsapp.core.constants.LocalizationConstants
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
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModelFactory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel

    private lateinit var newsDTO: NewsDTO
    private lateinit var newsRepository: NewsRepository

    private lateinit var categoryRecyclerViewAdapter: CategoryRecyclerViewAdapter
    private var newsRecyclerViewAdapter: NewsRecyclerViewAdapter? = null

    private lateinit var languageChangeBroadcastReceiver: LanguageChangeBroadcastReceiver
    private lateinit var intentFilter: IntentFilter

    override fun onCreate(savedInstanceState: Bundle?) {

        newsDTO = NewsRetrofitInstance.instance().create(NewsDTO::class.java)
        newsRepository = NewsRepositoryImpl(newsDTO)
        viewModelFactory = MainViewModelFactory(newsRepository, ApiConstants.getCategories(applicationContext),
            getCurrentLocale(applicationContext)!!.country.lowercase())
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.getNews(LocalizationConstants.getAvailableCountry(applicationContext))

        categoryRecyclerViewAdapter = CategoryRecyclerViewAdapter(ApiConstants.getCategories(applicationContext)) {
                category -> viewModel.changeSelectedCategory(category, LocalizationConstants.getAvailableCountry(applicationContext))
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.idRVCategories.adapter = categoryRecyclerViewAdapter

        languageChangeBroadcastReceiver = LanguageChangeBroadcastReceiver() {
            val localeCountry = getCurrentLocale(applicationContext)!!.country.lowercase();
            viewModel.changeCurrentLocale(localeCountry,
                LocalizationConstants.getAvailableCountry(applicationContext))
        }
        intentFilter = IntentFilter(Intent.ACTION_LOCALE_CHANGED)
        registerReceiver(languageChangeBroadcastReceiver, intentFilter)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        viewModel.state.observe(this) {
            when(it.status) {
                is MainViewStatus.Data -> {
                    binding.idLoadingContainerState.visibility = View.GONE
                    binding.idErrorContainerState.visibility = View.GONE

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
                    binding.idErrorContainerState.visibility = View.GONE

                    newsRecyclerViewAdapter = null
                }
                MainViewStatus.Started -> {
                    binding.idLoadingContainerState.visibility = View.GONE
                    binding.idDataContainerState.visibility = View.GONE
                    binding.idErrorContainerState.visibility = View.GONE

                    newsRecyclerViewAdapter = null
                }

            }
        }
    }

    fun getCurrentLocale(context: Context): Locale? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.resources.configuration.locales[0]
        } else {
            context.resources.configuration.locale
        }
    }

    override fun onDestroy() {
        unregisterReceiver(languageChangeBroadcastReceiver)
        super.onDestroy()
    }
}