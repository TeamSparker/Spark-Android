package com.spark.android.data.remote.repository

import com.spark.android.data.remote.entity.response.SignUpResponse
import okhttp3.Callback
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface AuthRepository {
    fun initKakaoUserId(initId: (String) -> Unit)

    fun getFcmToken(getFcmToken: (String) -> Unit)

    suspend fun postSignUp(
        nickname: String,
        kakaoUserId: String,
        fcmToken: String,
        profileImg: MultipartBody.Part?
    ): Result<SignUpResponse>

    fun saveAccessToken(accessToken: String)
}
