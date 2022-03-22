package com.spark.android.ui.alarmcenter.alarms.pagingsource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.spark.android.data.remote.entity.response.Alarm
import com.spark.android.data.remote.service.AlarmCenterService
import com.spark.android.ui.alarmcenter.alarms.AlarmType.Companion.ACTIVITY_ALARM
import com.spark.android.ui.alarmcenter.alarms.AlarmType.Companion.SERVICE_ALARM
import java.lang.IllegalArgumentException

class AlarmPagingSource(
    private val alarmType: Int,
    private val service: AlarmCenterService,
    private val limit: Int,
    private val initAlarmSticker: (Boolean) -> Unit
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
                    lastIdMap[currentIdKey + 1]
                }
                anchorPage?.nextKey != null -> {
                    lastIdMap[currentIdKey - 1]
                }
                else -> {
                    null
                }
            }
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Alarm> {
        return try {
            val idKey = currentIdKey++
            val lastId = params.key ?: -1
            val alarmData = when (alarmType) {
                ACTIVITY_ALARM -> service.getActivityAlarmList(lastId, limit).data
                SERVICE_ALARM -> service.getServiceAlarmList(lastId, limit).data
                else -> throw IllegalArgumentException("AlarmPagingSource AlarmType 오류")
            }
            initAlarmSticker(newActive = alarmData.newActive, newService = alarmData.newService)
            val alarmList = alarmData.alarms
            if (idKey == 1) {
                alarmList.first().isFirst = true
            }
            if (alarmList.size == limit) {
                lastIdMap[idKey + 1] = alarmList.last().noticeId
            }
            return LoadResult.Page(
                data = alarmList,
                prevKey = if (idKey <= 1) null else lastIdMap[idKey - 1],
                nextKey = if (alarmList.size < limit) null else lastIdMap[idKey + 1]
            )
        } catch (e: Exception) {
            Log.d(this.javaClass.toString(), e.message.toString())
            LoadResult.Error(e)
        }
    }

    private fun initAlarmSticker(newActive: Boolean?, newService: Boolean?) {
        when (alarmType) {
            ACTIVITY_ALARM -> newService?.let { initAlarmSticker(it) }
            SERVICE_ALARM -> newActive?.let { initAlarmSticker(it) }
        }
    }

    companion object {
        private const val FIRST_ID = -1
    }
}
