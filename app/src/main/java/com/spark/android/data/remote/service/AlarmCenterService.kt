package com.spark.android.data.remote.service

import com.spark.android.data.remote.entity.response.ActivityAlarmResponse
import com.spark.android.data.remote.entity.response.BaseResponse
import com.spark.android.data.remote.entity.response.NoDataResponse
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Query

interface AlarmCenterService {
    @PATCH("notice/active/read")
    suspend fun patchActivityAlarm(): NoDataResponse

    @GET("notice/active")
    suspend fun getActivityAlarmList(
        @Query("lastId") lastId: Int,
        @Query("size") size: Int
    ): BaseResponse<ActivityAlarmResponse>
}
