package com.teamsparker.android.data.remote.service

import com.teamsparker.android.data.remote.entity.response.AlarmResponse
import com.teamsparker.android.data.remote.entity.response.BaseResponse
import com.teamsparker.android.data.remote.entity.response.NoDataResponse
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
    ): BaseResponse<AlarmResponse>

    @PATCH("notice/service/read")
    suspend fun patchServiceAlarm(): NoDataResponse

    @GET("notice/service")
    suspend fun getServiceAlarmList(
        @Query("lastId") lastId: Int,
        @Query("size") size: Int
    ): BaseResponse<AlarmResponse>
}
