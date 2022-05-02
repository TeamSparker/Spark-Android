package com.spark.android.data.remote.service

import com.spark.android.data.remote.entity.response.NoDataResponse
import retrofit2.http.PATCH
import retrofit2.http.Path

interface HomeHabitRoomFinishService {

    @PATCH("room/{roomId}/read")
    suspend fun readFinishHabitRoom(
        @Path("roomId") roomId: Int
    ): NoDataResponse
}
