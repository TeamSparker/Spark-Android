package com.spark.android.data.remote.repository

import com.spark.android.data.remote.entity.response.BaseResponse
import com.spark.android.data.remote.entity.response.DoorbellResponse
import com.spark.android.data.remote.entity.response.NoDataResponse
import com.spark.android.data.remote.entity.response.SignUpResponse
import com.spark.android.data.remote.entity.response.VersionResponse
import com.spark.android.ui.intro.VersionUpdateState
import okhttp3.MultipartBody

interface AuthRepository {
    fun initKakaoUserId(initId: (String) -> Unit)

    fun unLinkKakaoAccount(initSuccessWithdraw: (Boolean) -> Unit)

    fun getFcmToken(getFcmToken: (String) -> Unit)

    fun saveAccessToken(accessToken: String)

    fun removeAccessToken()

    fun removeKakaoUserId()

    fun versionCheck(storeVersion: String, currentVersion: String): VersionUpdateState

    suspend fun getStoreVersion(): Result<BaseResponse<VersionResponse>>

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

    suspend fun postSignOut(): Result<NoDataResponse>

    suspend fun deleteUser(): Result<NoDataResponse>
}
