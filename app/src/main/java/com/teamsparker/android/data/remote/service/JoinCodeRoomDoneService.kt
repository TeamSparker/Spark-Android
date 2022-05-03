package com.teamsparker.android.data.remote.service

import com.teamsparker.android.data.remote.entity.response.NoDataResponse
import retrofit2.http.POST
import retrofit2.http.Path

interface JoinCodeRoomDoneService {

    @POST("room/{roomId}/enter")
    suspend fun setJoinCodeRoomDone(
        @Path("roomId") roomId: Int
    ): NoDataResponse
}
