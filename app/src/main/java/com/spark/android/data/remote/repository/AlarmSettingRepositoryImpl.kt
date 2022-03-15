package com.spark.android.data.remote.repository

import com.spark.android.data.remote.datasource.AlarmSettingDataSource
import com.spark.android.data.remote.entity.response.AlarmSettingResponse
import com.spark.android.data.remote.entity.response.BaseResponse
import com.spark.android.data.remote.entity.response.NoDataResponse
import javax.inject.Inject

class AlarmSettingRepositoryImpl @Inject constructor(
    private val alarmSettingDataSource: AlarmSettingDataSource
) : AlarmSettingRepository {
    override suspend fun getAlarmSetting(): Result<BaseResponse<AlarmSettingResponse>> =
        kotlin.runCatching { alarmSettingDataSource.getAlarmSetting() }

    override suspend fun patchAlarmSetting(category: String): Result<NoDataResponse> =
        kotlin.runCatching { alarmSettingDataSource.patchAlarmSetting(category) }
}
