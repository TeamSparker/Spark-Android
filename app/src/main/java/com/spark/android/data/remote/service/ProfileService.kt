package com.spark.android.data.remote.service

import com.spark.android.data.remote.entity.response.BaseResponse
import com.spark.android.data.remote.entity.response.ProfileResponse
import retrofit2.http.GET

interface ProfileService {
    @GET("user/profile")
    suspend fun getProfile(): BaseResponse<ProfileResponse>
}
