package com.spark.android.data.remote.repository

import androidx.paging.PagingData
import com.spark.android.data.remote.entity.response.Alarm
import com.spark.android.data.remote.entity.response.NoDataResponse
import kotlinx.coroutines.flow.Flow

interface AlarmCenterRepository {
    suspend fun patchActivityAlarm(): Result<NoDataResponse>
    fun getActivityAlarmList(size: Int): Flow<PagingData<Alarm>>
}
