package com.teamsparker.android.data.remote.service

import com.teamsparker.android.data.remote.calladapter.NetworkState
import com.teamsparker.android.data.remote.entity.response.BaseResponse
import com.teamsparker.android.data.remote.entity.response.HabitRoomTimeLine
import retrofit2.http.GET
import retrofit2.http.Path

interface HabitRoomTimeLineService {
    @GET("room/{roomId}/timeline")
    suspend fun getHabitRoomRimeLine(
        @Path("roomId") roomId: Int
    ): NetworkState<BaseResponse<HabitRoomTimeLine>>
}
