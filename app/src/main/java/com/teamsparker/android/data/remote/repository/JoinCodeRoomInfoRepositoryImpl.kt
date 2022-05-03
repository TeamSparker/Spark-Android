package com.teamsparker.android.data.remote.repository

import com.teamsparker.android.data.remote.service.JoinCodeRoomInfoService
import javax.inject.Inject

class JoinCodeRoomInfoRepositoryImpl @Inject constructor(
    private val joinCodeRoomInfoService: JoinCodeRoomInfoService
) :JoinCodeRoomInfoRepository{
    override suspend fun getJoinCodeRoomInfo(
        code: String
    ) = joinCodeRoomInfoService.getJoinCodeRoomInfo(code)
}
