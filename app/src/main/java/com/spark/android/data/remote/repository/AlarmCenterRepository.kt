package com.spark.android.data.remote.repository

import androidx.paging.PagingData
import com.spark.android.data.remote.entity.response.ActivityAlarm
import kotlinx.coroutines.flow.Flow

interface AlarmCenterRepository {
    fun getActivityAlarmList(size: Int): Flow<PagingData<ActivityAlarm>>
}
