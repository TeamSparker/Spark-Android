package com.spark.android.data.remote.repository

import com.spark.android.data.remote.entity.response.BaseResponse
import com.spark.android.data.remote.entity.response.WaitingRoomInfoResponse
import com.spark.android.data.remote.service.WaitingRoomInfoService
import javax.inject.Inject

class WaitingRoomInfoRepositoryImpl @Inject constructor(
    private val waitingRoomInfoService: WaitingRoomInfoService
) :WaitingRoomInfoRepository{
    override suspend fun getWaitingRoomInfo(
        roomId: Int
    ) = waitingRoomInfoService.getWaitingRoomInfo(roomId)
}