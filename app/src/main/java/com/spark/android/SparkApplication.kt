package com.spark.android

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.spark.android.data.remote.LocalPreferences
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SparkApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        LocalPreferences.init(this)
        // Kakao SDK 초기화
        KakaoSdk.init(this, getString(R.string.kakao_native_app_key))
        // Kakao 키 해시 호출하기
        Utility.getKeyHash(this)
    }

}
