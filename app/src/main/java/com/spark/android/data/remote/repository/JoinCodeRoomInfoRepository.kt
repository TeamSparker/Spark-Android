package com.spark.android.data.remote.repository

import com.spark.android.data.remote.entity.response.BaseResponse
import com.spark.android.data.remote.entity.response.JoinCodeRoomInfoResponse

interface JoinCodeRoomInfoRepository {

    suspend fun getJoinCodeRoomInfo(code:String):BaseResponse<JoinCodeRoomInfoResponse>
}