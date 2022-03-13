package com.spark.android.data.remote.repository

import com.spark.android.data.remote.entity.response.BaseResponse
import com.spark.android.data.remote.entity.response.ProfileResponse

interface ProfileRepository {
    suspend fun getProfile(): Result<BaseResponse<ProfileResponse>>
}
