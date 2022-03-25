package com.spark.android.data.remote.repository

import com.spark.android.data.remote.entity.response.BaseResponse
import com.spark.android.data.remote.entity.response.DoorbellResponse
import com.spark.android.data.remote.entity.response.NoDataResponse
import com.spark.android.data.remote.entity.response.SignUpResponse
import okhttp3.Callback
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface AuthRepository {
    fun initKakaoUserId(initId: (String) -> Unit)

    fun getFcmToken(getFcmToken: (String) -> Unit)

    fun saveAccessToken(accessToken: String)

    fun removeAccessToken()

    fun removeKakaoUserId()

    suspend fun postSignUp(
        nickname: String,
        kakaoUserId: String,
        fcmToken: String,
        profileImg: MultipartBody.Part?
    ): Result<BaseResponse<SignUpResponse>>

    suspend fun getAccessToken(
        socialId: String,
        fcmToken: String
    ): Result<BaseResponse<DoorbellResponse>>

    suspend fun deleteUser(): Result<NoDataResponse>
}
