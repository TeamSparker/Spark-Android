package com.spark.android.data.remote.datasource

import com.spark.android.data.remote.entity.response.AlarmSettingResponse
import com.spark.android.data.remote.entity.response.BaseResponse
import com.spark.android.data.remote.entity.response.NoDataResponse
import com.spark.android.data.remote.service.AlarmSettingService
import javax.inject.Inject

class AlarmSettingDataSourceImpl @Inject constructor(
    private val alarmSettingService: AlarmSettingService
) : AlarmSettingDataSource {
    override suspend fun getAlarmSetting(): BaseResponse<AlarmSettingResponse> =
        alarmSettingService.getAlarmSetting()

    override suspend fun patchAlarmSetting(category: String): NoDataResponse =
        alarmSettingService.patchAlarmSetting(category)
}
