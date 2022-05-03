package com.teamsparker.android.data.remote.service

import com.teamsparker.android.data.remote.entity.response.BaseResponse
import com.teamsparker.android.data.remote.entity.response.WaitingRoomInfoResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface WaitingRoomInfoService {

    @GET("room/{roomId}/waiting")
    suspend fun getWaitingRoomInfo (
        @Path("roomId") roomId : Int
    ): BaseResponse<WaitingRoomInfoResponse>

}
