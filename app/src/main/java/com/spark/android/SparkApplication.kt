package com.spark.android

import android.app.Application
import android.content.Context
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SparkApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Kakao SDK 초기화
        KakaoSdk.init(this, getString(R.string.kakao_native_app_key))

        // Kakao 키 해시 호출하기
        Utility.getKeyHash(this)
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
