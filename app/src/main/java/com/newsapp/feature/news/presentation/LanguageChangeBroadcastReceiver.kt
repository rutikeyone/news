package com.newsapp.feature.news.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class LanguageChangeBroadcastReceiver(
    private val languageChangeCallback: () -> Unit
) : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_LOCALE_CHANGED) {
            languageChangeCallback()
        }
    }
}