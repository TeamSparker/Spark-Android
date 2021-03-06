package com.teamsparker.android.data.remote.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.teamsparker.android.data.remote.entity.response.Alarm
import com.teamsparker.android.data.remote.entity.response.NoDataResponse
import com.teamsparker.android.data.remote.service.AlarmCenterService
import com.teamsparker.android.ui.alarmcenter.alarms.AlarmType.Companion.ACTIVITY_ALARM
import com.teamsparker.android.ui.alarmcenter.alarms.AlarmType.Companion.SERVICE_ALARM
import com.teamsparker.android.ui.alarmcenter.alarms.pagingsource.AlarmPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AlarmCenterDataSourceImpl @Inject constructor(
    private val alarmCenterService: AlarmCenterService
) : AlarmCenterDataSource {
    override suspend fun patchActivityAlarm(): NoDataResponse =
        alarmCenterService.patchActivityAlarm()

    override fun getActivityAlarmList(
        size: Int,
        initAlarmSticker: (Boolean) -> Unit
    ): Flow<PagingData<Alarm>> =
        Pager(
            config = PagingConfig(pageSize = size, enablePlaceholders = false),
            pagingSourceFactory = {
                AlarmPagingSource(ACTIVITY_ALARM, alarmCenterService, size, initAlarmSticker)
            }
        ).flow

    override suspend fun patchServiceAlarm(): NoDataResponse =
        alarmCenterService.patchServiceAlarm()

    override fun getServiceAlarmList(
        size: Int,
        initAlarmSticker: (Boolean) -> Unit
    ): Flow<PagingData<Alarm>> =
        Pager(
            config = PagingConfig(pageSize = size, enablePlaceholders = false),
            pagingSourceFactory = {
                AlarmPagingSource(SERVICE_ALARM, alarmCenterService, size, initAlarmSticker)
            }
        ).flow
}
