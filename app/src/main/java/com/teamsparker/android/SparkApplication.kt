package com.teamsparker.android

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.teamsparker.android.data.remote.LocalPreferences
import com.teamsparker.android.util.CheckForeground
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class SparkApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initTimber()
        LocalPreferences.init(this)
        // Kakao SDK 초기화
        KakaoSdk.init(this, getString(R.string.kakao_native_app_key))
        // Kakao 키 해시 호출하기
        Timber.tag("kakao_keyHash").d(Utility.getKeyHash(this))
        // Foreground, Background 감지 클래스 초기화
        CheckForeground.init(this@SparkApplication)
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}
