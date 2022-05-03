package com.teamsparker.android.data.remote.repository

import com.teamsparker.android.data.remote.entity.response.AlarmSettingResponse
import com.teamsparker.android.data.remote.entity.response.BaseResponse
import com.teamsparker.android.data.remote.entity.response.NoDataResponse

interface AlarmSettingRepository {
    fun saveAlarmSettingLocalSaved(isSaved: Boolean)

    fun saveAlarmSettingValue(alarmSettingValue: AlarmSettingResponse)

    fun patchAlarmSettingValue(category: String)

    fun getAlarmSettingLocalSaved(): Boolean

    fun getAlarmSettingValue(): AlarmSettingResponse

    suspend fun getAlarmSetting(): Result<BaseResponse<AlarmSettingResponse>>

    suspend fun patchAlarmSetting(category: String): Result<NoDataResponse>
}
