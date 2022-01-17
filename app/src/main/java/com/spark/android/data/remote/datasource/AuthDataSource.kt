package com.spark.android.data.remote.datasource

import com.spark.android.data.remote.entity.response.SignUpResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface AuthDataSource {
    suspend fun postSignUp(
        map: Map<String, RequestBody>,
        profileImg: MultipartBody.Part?
    ): SignUpResponse
}