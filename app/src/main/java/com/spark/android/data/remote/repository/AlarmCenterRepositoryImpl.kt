package com.spark.android.data.remote.repository

import androidx.paging.PagingData
import com.spark.android.data.remote.datasource.AlarmCenterDataSource
import com.spark.android.data.remote.entity.response.Alarm
import com.spark.android.data.remote.entity.response.NoDataResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AlarmCenterRepositoryImpl @Inject constructor(
    private val alarmCenterDataSource: AlarmCenterDataSource
) : AlarmCenterRepository {
    override suspend fun patchActivityAlarm(): Result<NoDataResponse> =
        kotlin.runCatching { alarmCenterDataSource.patchActivityAlarm() }

    override fun getActivityAlarmList(size: Int): Flow<PagingData<Alarm>> =
        alarmCenterDataSource.getActivityAlarmList(size)

    override suspend fun patchServiceAlarm(): Result<NoDataResponse> =
        kotlin.runCatching { alarmCenterDataSource.patchServiceAlarm() }

    override fun getServiceAlarmList(size: Int): Flow<PagingData<Alarm>> =
        alarmCenterDataSource.getServiceAlarmList(size)
}
