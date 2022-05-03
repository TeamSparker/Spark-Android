package com.teamsparker.android.data.remote.service

import com.teamsparker.android.data.remote.entity.response.BaseResponse
import com.teamsparker.android.data.remote.entity.response.RefreshResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface RefreshService {

    @GET("room/{roomId}/waiting/member")
    suspend fun getRefresh(
        @Path("roomId") roomId: Int
    ):BaseResponse<RefreshResponse>
}
