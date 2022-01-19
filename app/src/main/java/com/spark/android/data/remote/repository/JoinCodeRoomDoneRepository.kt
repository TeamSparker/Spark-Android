package com.spark.android.data.remote.repository

import com.spark.android.data.remote.entity.response.NoDataResponse

interface JoinCodeRoomDoneRepository {

    suspend fun setJoinCodeRoomDone(roomId: Int): NoDataResponse
}