package com.teamsparker.android.data.remote.datasource

import com.teamsparker.android.data.remote.calladapter.NetworkState
import com.teamsparker.android.data.remote.entity.response.BaseResponse
import com.teamsparker.android.data.remote.entity.response.HabitRoomTimeLine

interface HabitRoomTimeLineDataSource {

    suspend fun getHabitRoomTimeLine(roomId: Int): NetworkState<BaseResponse<HabitRoomTimeLine>>
}
