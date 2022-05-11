package com.teamsparker.android.data.remote.service

import com.teamsparker.android.data.remote.entity.response.NoDataResponse
import retrofit2.http.DELETE
import retrofit2.http.Path

interface DeleteRoomService {

    @DELETE("room/{roomId}")
    suspend fun deleteWaitingRoom(
        @Path("roomId") roomId: Int
    ): NoDataResponse
}
