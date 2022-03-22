package com.spark.android.data.remote.datasource

import androidx.paging.PagingData
import com.spark.android.data.remote.entity.response.Alarm
import com.spark.android.data.remote.entity.response.NoDataResponse
import kotlinx.coroutines.flow.Flow

interface AlarmCenterDataSource {
    suspend fun patchActivityAlarm(): NoDataResponse
    fun getActivityAlarmList(size: Int): Flow<PagingData<Alarm>>
}
