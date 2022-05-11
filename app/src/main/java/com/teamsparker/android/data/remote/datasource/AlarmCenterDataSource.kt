package com.teamsparker.android.data.remote.datasource

import androidx.paging.PagingData
import com.teamsparker.android.data.remote.entity.response.Alarm
import com.teamsparker.android.data.remote.entity.response.NoDataResponse
import kotlinx.coroutines.flow.Flow

interface AlarmCenterDataSource {
    suspend fun patchActivityAlarm(): NoDataResponse

    fun getActivityAlarmList(
        size: Int,
        initAlarmSticker: (Boolean) -> Unit
    ): Flow<PagingData<Alarm>>

    suspend fun patchServiceAlarm(): NoDataResponse

    fun getServiceAlarmList(
        size: Int,
        initAlarmSticker: (Boolean) -> Unit
    ): Flow<PagingData<Alarm>>
}
