package com.teamsparker.android.data.remote.repository

import com.teamsparker.android.data.remote.entity.response.BaseResponse
import com.teamsparker.android.data.remote.entity.response.JoinCodeRoomInfoResponse

interface JoinCodeRoomInfoRepository {

    suspend fun getJoinCodeRoomInfo(code:String):BaseResponse<JoinCodeRoomInfoResponse>
}
