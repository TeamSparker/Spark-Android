package com.spark.android.data.remote.repository

import com.kakao.sdk.auth.model.OAuthToken

interface SignInRepository {
    fun startKakaoLogin(kakaoLoginCallback: (OAuthToken?, Throwable?) -> Unit)
}
