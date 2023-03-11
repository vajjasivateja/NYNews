package com.example.app.nynews

import android.content.Context
import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NYNewsApplication: MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

    companion object {
        val TAG = NYNewsApplication::class.java.simpleName
        lateinit var appContext: Context
    }
}