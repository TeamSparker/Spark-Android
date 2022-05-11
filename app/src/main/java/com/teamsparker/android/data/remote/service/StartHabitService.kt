package com.teamsparker.android.data.remote.service

import com.teamsparker.android.data.remote.entity.response.NoDataResponse
import retrofit2.http.POST
import retrofit2.http.Path

interface StartHabitService {

    @POST("room/{roomId}/start")
    suspend fun startHabit(
        @Path("roomId") roomId: Int
    ): NoDataResponse
}
