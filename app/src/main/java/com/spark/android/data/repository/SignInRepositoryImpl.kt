package com.spark.android.data.repository

import android.content.Context
import android.util.Log
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import javax.inject.Inject

class SignInRepositoryImpl @Inject constructor(
    private val context: Context
) : SignInRepository {
    override fun startKakaoLogin(): Int =
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            KAKAO_TALK_LOGIN
        } else {
            KAKAO_ACCOUNT_LOGIN
        }


    companion object {
        const val KAKAO_TALK_LOGIN = 0
        const val KAKAO_ACCOUNT_LOGIN = 1
    }
}
