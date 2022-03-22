package com.spark.android.data.remote.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.spark.android.data.remote.entity.response.ActivityAlarm
import com.spark.android.data.remote.service.AlarmCenterService
import com.spark.android.ui.alarmcenter.acitivityalarm.pagingsource.ActivityAlarmPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AlarmCenterDataSourceImpl @Inject constructor(
    private val alarmCenterService: AlarmCenterService
) : AlarmCenterDataSource {
    override fun getActivityAlarmList(size: Int): Flow<PagingData<ActivityAlarm>> =
        Pager(
            config = PagingConfig(pageSize = size, enablePlaceholders = false),
            pagingSourceFactory = { ActivityAlarmPagingSource(alarmCenterService, size) }
        ).flow
}
