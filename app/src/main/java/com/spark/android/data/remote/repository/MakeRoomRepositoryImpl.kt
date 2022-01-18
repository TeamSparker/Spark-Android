package com.spark.android.data.remote.repository

import com.spark.android.data.remote.entity.request.MakeRoomRequest
import com.spark.android.data.remote.entity.response.BaseResponse
import com.spark.android.data.remote.entity.response.MakeRoomResponse
import com.spark.android.data.remote.service.MakeRoomService
import javax.inject.Inject

class MakeRoomRepositoryImpl @Inject constructor(
    private val makeRoomService: MakeRoomService
) : MakeRoomRepository {
    override suspend fun makeRoom(
        body: MakeRoomRequest
    ) = makeRoomService.makeRoom(body)
}