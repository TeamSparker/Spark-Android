package com.spark.android.data.remote.repository

import com.spark.android.data.remote.service.JoinCodeRoomDoneService
import javax.inject.Inject

class JoinCodeRoomDoneRepositoryImpl @Inject constructor(
    private val joinCodeRoomDoneService: JoinCodeRoomDoneService
) : JoinCodeRoomDoneRepository {
    override suspend fun setJoinCodeRoomDone(
        roomId: Int
    ) = kotlin.runCatching {
        joinCodeRoomDoneService.setJoinCodeRoomDone(roomId)
    }
}
