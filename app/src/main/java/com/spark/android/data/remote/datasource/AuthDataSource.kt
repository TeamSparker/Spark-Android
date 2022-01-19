package com.spark.android.data.remote.datasource

import com.spark.android.data.remote.entity.response.BaseResponse
import com.spark.android.data.remote.entity.response.DoorbellResponse
import com.spark.android.data.remote.entity.response.SignUpResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface AuthDataSource {
    suspend fun postSignUp(
        map: Map<String, RequestBody>,
        profileImg: MultipartBody.Part?
    ): BaseResponse<SignUpResponse>

    suspend fun getAccessToken(socialId: String, fcmToken: String): BaseResponse<DoorbellResponse>
}
