package com.teamsparker.android.data.remote.repository

import com.teamsparker.android.data.local.datasource.LocalPreferencesDataSource
import com.teamsparker.android.data.remote.datasource.AlarmSettingDataSource
import com.teamsparker.android.data.remote.entity.response.AlarmSettingResponse
import com.teamsparker.android.data.remote.entity.response.BaseResponse
import com.teamsparker.android.data.remote.entity.response.NoDataResponse
import javax.inject.Inject

class AlarmSettingRepositoryImpl @Inject constructor(
    private val alarmSettingDataSource: AlarmSettingDataSource,
    private val localPreferencesDataSource: LocalPreferencesDataSource
) : AlarmSettingRepository {
    override fun saveAlarmSettingLocalSaved(isSaved: Boolean) {
        localPreferencesDataSource.saveAlarmSettingLocalSaved(isSaved)
    }

    override fun saveAlarmSettingValue(alarmSettingValue: AlarmSettingResponse) {
        localPreferencesDataSource.saveAlarmSettingValue(alarmSettingValue)
    }

    override fun patchAlarmSettingValue(category: String) {
        localPreferencesDataSource.patchAlarmSettingValue(category)
    }

    override fun getAlarmSettingLocalSaved(): Boolean =
        localPreferencesDataSource.getAlarmSettingLocalSaved()

    override fun getAlarmSettingValue(): AlarmSettingResponse =
        localPreferencesDataSource.getAlarmSettingValue()

    override suspend fun getAlarmSetting(): Result<BaseResponse<AlarmSettingResponse>> =
        kotlin.runCatching { alarmSettingDataSource.getAlarmSetting() }

    override suspend fun patchAlarmSetting(category: String): Result<NoDataResponse> =
        kotlin.runCatching { alarmSettingDataSource.patchAlarmSetting(category) }
}
