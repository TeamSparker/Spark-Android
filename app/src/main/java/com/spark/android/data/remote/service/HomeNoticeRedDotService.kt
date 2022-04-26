package com.spark.android.data.remote.service

import com.spark.android.data.remote.entity.response.BaseResponse
import com.spark.android.data.remote.entity.response.HomeNoticeRedDotResponese
import retrofit2.http.GET

interface HomeNoticeRedDotService {

    @GET("notice/new")
    suspend fun getHomeNoticeRedDot(): BaseResponse<HomeNoticeRedDotResponese>
}