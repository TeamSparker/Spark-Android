package com.teamsparker.android.data.remote.service

import com.teamsparker.android.data.remote.entity.request.SetStatusRequest
import com.teamsparker.android.data.remote.entity.response.NoDataResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface SetStatusService {

    @POST("room/{roomId}/status")
    suspend fun setStatus(
        @Path("roomId") roomId: Int,
        @Body body: SetStatusRequest,
    ): NoDataResponse
}
