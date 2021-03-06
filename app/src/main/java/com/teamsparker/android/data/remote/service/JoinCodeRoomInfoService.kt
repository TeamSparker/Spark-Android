package com.teamsparker.android.data.remote.service

import com.teamsparker.android.data.remote.entity.response.BaseResponse
import com.teamsparker.android.data.remote.entity.response.JoinCodeRoomInfoResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface JoinCodeRoomInfoService {

    @GET("room/code/{code}")
    suspend fun getJoinCodeRoomInfo(
        @Path ("code") code : String
    ) : BaseResponse<JoinCodeRoomInfoResponse>
}
