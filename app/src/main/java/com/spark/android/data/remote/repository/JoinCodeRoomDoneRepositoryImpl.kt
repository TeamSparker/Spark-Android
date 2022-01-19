package com.spark.android.data.remote.repository

import com.spark.android.data.remote.entity.response.NoDataResponse
import com.spark.android.data.remote.service.JoinCodeRoomDoneService
import com.spark.android.data.remote.service.JoinCodeRoomInfoService
import javax.inject.Inject

class JoinCodeRoomDoneRepositoryImpl @Inject constructor(
    private val joinCodeRoomDoneService: JoinCodeRoomDoneService
) : JoinCodeRoomDoneRepository {
    override suspend fun setJoinCodeRoomDone(
        roomId: Int
    ) = joinCodeRoomDoneService.setJoinCodeRoomDone(roomId)
}