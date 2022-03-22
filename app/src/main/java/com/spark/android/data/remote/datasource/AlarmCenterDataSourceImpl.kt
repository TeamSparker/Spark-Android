package com.spark.android.data.remote.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.spark.android.data.remote.entity.response.Alarm
import com.spark.android.data.remote.entity.response.NoDataResponse
import com.spark.android.data.remote.service.AlarmCenterService
import com.spark.android.ui.alarmcenter.acitivityalarm.pagingsource.AlarmPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AlarmCenterDataSourceImpl @Inject constructor(
    private val alarmCenterService: AlarmCenterService
) : AlarmCenterDataSource {
    override suspend fun patchActivityAlarm(): NoDataResponse =
        alarmCenterService.patchActivityAlarm()

    override fun getActivityAlarmList(size: Int): Flow<PagingData<Alarm>> =
        Pager(
            config = PagingConfig(pageSize = size, enablePlaceholders = false),
            pagingSourceFactory = { AlarmPagingSource(alarmCenterService, size) }
        ).flow

    override suspend fun patchServiceAlarm(): NoDataResponse =
        alarmCenterService.patchServiceAlarm()

    override fun getServiceAlarmList(size: Int): Flow<PagingData<Alarm>> =
        Pager(
            config = PagingConfig(pageSize = size, enablePlaceholders = false),
            pagingSourceFactory = { AlarmPagingSource(alarmCenterService, size) }
        ).flow
}
