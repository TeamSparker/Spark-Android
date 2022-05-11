package com.teamsparker.android.data.remote.datasource

import com.teamsparker.android.data.remote.entity.response.AlarmSettingResponse
import com.teamsparker.android.data.remote.entity.response.BaseResponse
import com.teamsparker.android.data.remote.entity.response.NoDataResponse

interface AlarmSettingDataSource {
    suspend fun getAlarmSetting(): BaseResponse<AlarmSettingResponse>

    suspend fun patchAlarmSetting(category: String): NoDataResponse
}
