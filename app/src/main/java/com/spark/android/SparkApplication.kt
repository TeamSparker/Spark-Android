package com.spark.android

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SparkApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}