package com.spark.android.data.remote.datasource

import androidx.paging.PagingData
import com.spark.android.data.remote.entity.response.ActivityAlarm
import kotlinx.coroutines.flow.Flow

interface AlarmCenterDataSource {
    fun getActivityAlarmList(size: Int): Flow<PagingData<ActivityAlarm>>
}
