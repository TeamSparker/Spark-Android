package com.spark.android.data.remote.service

import com.spark.android.data.remote.entity.response.NoDataResponse
import retrofit2.http.DELETE
import retrofit2.http.Path

interface LeaveRoomService {

    @DELETE("room/{roomId}/out")
    suspend fun leaveRoom(
        @Path("roomId") roomId: Int
    ):NoDataResponse
}