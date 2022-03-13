package com.spark.android.data.remote.repository

import com.spark.android.data.remote.entity.response.BaseResponse
import com.spark.android.data.remote.entity.response.NoDataResponse
import com.spark.android.data.remote.entity.response.ProfileResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface ProfileRepository {
    suspend fun getProfile(): Result<BaseResponse<ProfileResponse>>

    suspend fun patchProfile(
        nickname: String,
        profileImg: MultipartBody.Part?
    ): Result<NoDataResponse>
}
