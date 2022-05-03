package com.teamsparker.android.data.remote.repository

import androidx.paging.PagingData
import com.teamsparker.android.data.remote.entity.response.Alarm
import com.teamsparker.android.data.remote.entity.response.NoDataResponse
import kotlinx.coroutines.flow.Flow

interface AlarmCenterRepository {
    suspend fun patchActivityAlarm(): Result<NoDataResponse>

    fun getActivityAlarmList(
        size: Int,
        initAlarmSticker: (Boolean) -> Unit
    ): Flow<PagingData<Alarm>>

    suspend fun patchServiceAlarm(): Result<NoDataResponse>

    fun getServiceAlarmList(
        size: Int,
        initAlarmSticker: (Boolean) -> Unit
    ): Flow<PagingData<Alarm>>
}
