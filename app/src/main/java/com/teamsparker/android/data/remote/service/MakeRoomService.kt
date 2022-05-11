package com.teamsparker.android.data.remote.service

import com.teamsparker.android.data.remote.entity.request.MakeRoomRequest
import com.teamsparker.android.data.remote.entity.response.BaseResponse
import com.teamsparker.android.data.remote.entity.response.MakeRoomResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface MakeRoomService {

    @POST("room")
    suspend fun makeRoom (
        @Body body : MakeRoomRequest
    ): BaseResponse<MakeRoomResponse>
}
