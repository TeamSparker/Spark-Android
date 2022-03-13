package com.spark.android.data.remote.repository

import com.spark.android.data.remote.entity.response.AlarmSettingResponse
import com.spark.android.data.remote.entity.response.BaseResponse
import com.spark.android.data.remote.entity.response.NoDataResponse

interface AlarmSettingRepository {
    suspend fun getAlarmSetting(): Result<BaseResponse<AlarmSettingResponse>>

    suspend fun patchAlarmSetting(category: String): Result<NoDataResponse>
}
