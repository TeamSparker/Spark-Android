package com.spark.android.data.remote.repository

import androidx.paging.PagingData
import com.spark.android.data.remote.datasource.AlarmCenterDataSource
import com.spark.android.data.remote.entity.response.ActivityAlarm
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AlarmCenterRepositoryImpl @Inject constructor(
    private val alarmCenterDataSource: AlarmCenterDataSource
) : AlarmCenterRepository {
    override fun getActivityAlarmList(size: Int): Flow<PagingData<ActivityAlarm>> =
        alarmCenterDataSource.getActivityAlarmList(size)
}
