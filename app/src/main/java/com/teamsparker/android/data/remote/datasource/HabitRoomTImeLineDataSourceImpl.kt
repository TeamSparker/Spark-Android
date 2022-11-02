package com.teamsparker.android.data.remote.datasource

import com.teamsparker.android.data.remote.calladapter.NetworkState
import com.teamsparker.android.data.remote.entity.response.BaseResponse
import com.teamsparker.android.data.remote.entity.response.HabitRoomTimeLine
import com.teamsparker.android.data.remote.service.HabitRoomTimeLineService
import javax.inject.Inject

class HabitRoomTImeLineDataSourceImpl @Inject constructor(
    private val habitRoomTimeLineService: HabitRoomTimeLineService
) : HabitRoomTimeLineDataSource {
    override suspend fun getHabitRoomTimeLine(roomId: Int): NetworkState<BaseResponse<HabitRoomTimeLine>> =
        habitRoomTimeLineService.getHabitRoomRimeLine(roomId)
}
