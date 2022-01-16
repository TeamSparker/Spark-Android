package com.spark.android.data.remote.service

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class KakaoLoginService @Inject constructor(
    @ActivityContext private val context: Context
) {
    fun startKakaoLogin(kakaoLoginCallback: (OAuthToken?, Throwable?) -> Unit) {
        val kakaoLoginState = if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            KAKAO_TALK_LOGIN
        } else {
            KAKAO_ACCOUNT_LOGIN
        }

        when (kakaoLoginState) {
            KAKAO_TALK_LOGIN -> UserApiClient.instance.loginWithKakaoTalk(
                context,
                callback = kakaoLoginCallback
            )
            KAKAO_ACCOUNT_LOGIN -> UserApiClient.instance.loginWithKakaoAccount(
                context,
                callback = kakaoLoginCallback
            )
        }
    }

    companion object {
        const val KAKAO_TALK_LOGIN = 0
        const val KAKAO_ACCOUNT_LOGIN = 1
    }
}
