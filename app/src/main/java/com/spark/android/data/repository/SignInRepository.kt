package com.spark.android.data.repository

import com.kakao.sdk.auth.model.OAuthToken

interface SignInRepository {
    fun startKakaoLogin(kakaoLoginCallback: (OAuthToken?, Throwable?) -> Unit)
}
