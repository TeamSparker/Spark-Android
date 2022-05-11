package com.teamsparker.android.data.remote.service

import com.teamsparker.android.data.remote.entity.response.AlarmSettingResponse
import com.teamsparker.android.data.remote.entity.response.BaseResponse
import com.teamsparker.android.data.remote.entity.response.NoDataResponse
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Query

interface AlarmSettingService {
    @GET("notice/setting")
    suspend fun getAlarmSetting(): BaseResponse<AlarmSettingResponse>

    @PATCH("notice/setting")
    suspend fun patchAlarmSetting(
        @Query("category") category: String
    ): NoDataResponse
}
