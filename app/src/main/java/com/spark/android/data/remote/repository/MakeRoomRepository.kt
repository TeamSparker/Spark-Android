package com.spark.android.data.remote.repository

import com.spark.android.data.remote.entity.request.MakeRoomRequest
import com.spark.android.data.remote.entity.response.BaseResponse
import com.spark.android.data.remote.entity.response.MakeRoomResponse

interface MakeRoomRepository {

    suspend fun makeRoom(body: MakeRoomRequest): Result<BaseResponse<MakeRoomResponse>>
}
