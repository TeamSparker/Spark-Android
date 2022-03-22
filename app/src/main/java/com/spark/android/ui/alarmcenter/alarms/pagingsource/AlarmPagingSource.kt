package com.spark.android.ui.alarmcenter.alarms.pagingsource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.spark.android.data.remote.entity.response.Alarm
import com.spark.android.data.remote.service.AlarmCenterService
import com.spark.android.ui.alarmcenter.alarms.AlarmType
import com.spark.android.ui.alarmcenter.alarms.AlarmType.Companion.ACTIVITY_ALARM
import com.spark.android.ui.alarmcenter.alarms.AlarmType.Companion.SERVICE_ALARM
import java.lang.IllegalArgumentException

class AlarmPagingSource(
    private val alarmType: Int,
    private val service: AlarmCenterService,
    private val limit: Int
) : PagingSource<Int, Alarm>() {
    private var currentIdKey: Int = 1
    private val lastIdMap = hashMapOf<Int, Int>()

    init {
        initFirstId()
    }

    private fun initFirstId() {
        lastIdMap[1] = FIRST_ID
    }

    override fun getRefreshKey(state: PagingState<Int, Alarm>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            when {
                anchorPage?.prevKey != null -> {
                    lastIdMap[++currentIdKey]
                }
                anchorPage?.nextKey != null -> {
                    lastIdMap[--currentIdKey]
                }
                else -> {
                    null
                }
            }
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Alarm> {
        return try {
            val idKey = currentIdKey
            val lastId = params.key ?: -1
            val activityAlarmList = when (alarmType) {
                ACTIVITY_ALARM -> service.getActivityAlarmList(lastId, limit).data.alarms
                SERVICE_ALARM -> service.getServiceAlarmList(lastId, limit).data.alarms
                else -> throw IllegalArgumentException("AlarmPagingSource AlarmType 오류")
            }
            if (activityAlarmList.size == limit) {
                lastIdMap[idKey + 1] = activityAlarmList.last().noticeId
            }
            return LoadResult.Page(
                data = activityAlarmList,
                prevKey = if (idKey <= 1) null else lastIdMap[idKey - 1],
                nextKey = if (activityAlarmList.size < limit) null else lastIdMap[idKey + 1]
            )
        } catch (e: Exception) {
            Log.d(this.javaClass.toString(), e.message.toString())
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val FIRST_ID = -1
    }
}
