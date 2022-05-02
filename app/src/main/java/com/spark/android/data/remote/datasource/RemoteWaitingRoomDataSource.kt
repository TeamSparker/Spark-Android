package com.spark.android.data.remote.datasource

import com.spark.android.data.remote.entity.response.BaseResponse
import com.spark.android.data.remote.entity.response.NoDataResponse
import com.spark.android.data.remote.entity.response.WaitingRoomInfoResponse

interface RemoteWaitingRoomDataSource {

    suspend fun getWaitingRoomInfo(roomId:Int): BaseResponse<WaitingRoomInfoResponse>

    suspend fun deleteWaitingRoom(roomId: Int): NoDataResponse

    suspend fun leaveRoom(roomId: Int):NoDataResponse
}
