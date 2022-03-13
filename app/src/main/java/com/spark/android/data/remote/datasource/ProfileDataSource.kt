package com.spark.android.data.remote.datasource

import com.spark.android.data.remote.entity.response.BaseResponse
import com.spark.android.data.remote.entity.response.ProfileResponse

interface ProfileDataSource {
    suspend fun getProfile(): BaseResponse<ProfileResponse>
}
