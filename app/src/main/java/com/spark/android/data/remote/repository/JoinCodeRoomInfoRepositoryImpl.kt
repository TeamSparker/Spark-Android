package com.spark.android.data.remote.repository

import com.spark.android.data.remote.entity.response.BaseResponse
import com.spark.android.data.remote.entity.response.JoinCodeRoomInfoResponse
import com.spark.android.data.remote.service.JoinCodeRoomInfoService
import javax.inject.Inject

class JoinCodeRoomInfoRepositoryImpl @Inject constructor(
    private val joinCodeRoomInfoService: JoinCodeRoomInfoService
) :JoinCodeRoomInfoRepository{
    override suspend fun getJoinCodeRoomInfo(
        code: String
    ) = joinCodeRoomInfoService.getJoinCodeRoomInfo(code)
}