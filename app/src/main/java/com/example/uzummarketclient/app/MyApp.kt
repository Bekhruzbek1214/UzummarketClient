package com.example.uzummarketclient.app

import android.app.Application
import com.example.uzummarketclient.data.sourse.MyShar
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp:Application() {

    override fun onCreate() {
        super.onCreate()
        MyShar.init(this)
    }
}