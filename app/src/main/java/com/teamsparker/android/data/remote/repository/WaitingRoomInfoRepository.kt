package com.teamsparker.android.data.remote.repository

import com.teamsparker.android.data.remote.entity.response.BaseResponse
import com.teamsparker.android.data.remote.entity.response.NoDataResponse
import com.teamsparker.android.data.remote.entity.response.WaitingRoomInfoResponse

interface WaitingRoomInfoRepository {

    suspend fun getWaitingRoomInfo(roomId: Int): Result<BaseResponse<WaitingRoomInfoResponse>>

    suspend fun deleteWaitingRoom(roomId: Int): Result<NoDataResponse>

    suspend fun leaveRoom(roomId: Int): Result<NoDataResponse>

    fun setHomeToastMessage(message:String)

    fun setHomeToastMessageState(state:Boolean)
}
