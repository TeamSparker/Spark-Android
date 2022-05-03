package com.teamsparker.android.data.remote.repository

import com.teamsparker.android.data.remote.entity.request.MakeRoomRequest
import com.teamsparker.android.data.remote.entity.response.BaseResponse
import com.teamsparker.android.data.remote.entity.response.MakeRoomResponse

interface MakeRoomRepository {

    suspend fun makeRoom(body: MakeRoomRequest): Result<BaseResponse<MakeRoomResponse>>
}
