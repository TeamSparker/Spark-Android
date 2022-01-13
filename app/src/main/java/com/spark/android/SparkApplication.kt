package com.spark.android

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SparkApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }

    // 이거 컨텍스트 사용하려고 넣어놨는데 확인후 사용용
   init{
        instance = this
    }

    companion object {
        lateinit var instance: SparkApplication
        fun ApplicationContext() : Context {
            return instance.applicationContext
        }
    }
}