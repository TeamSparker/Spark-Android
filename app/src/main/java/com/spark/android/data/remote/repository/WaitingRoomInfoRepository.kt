package com.spark.android.data.remote.repository

import com.spark.android.data.remote.entity.response.BaseResponse
import com.spark.android.data.remote.entity.response.WaitingRoomInfoResponse

interface WaitingRoomInfoRepository {

    suspend fun getWaitingRoomInfo(roomId: Int): BaseResponse<WaitingRoomInfoResponse>
}