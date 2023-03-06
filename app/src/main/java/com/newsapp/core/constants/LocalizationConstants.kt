package com.newsapp.core.constants

import android.content.Context
import com.newsapp.R
import com.newsapp.feature.news.domain.entity.LocalizationEntity

class LocalizationConstants {
    companion object {
        fun getAvailableCountry(context: Context) : ArrayList<LocalizationEntity>  = arrayListOf<LocalizationEntity>(
            LocalizationEntity(1, "ae ", "AE"),
            LocalizationEntity(2, "pl", context.getString(R.string.poland)),
            LocalizationEntity(3, "ru", context.getString(R.string.russia)),
            LocalizationEntity(4, "tr", context.getString(R.string.turkey)),
            LocalizationEntity(5, "us", context.getString(R.string.usa))
        )

    }
}