package com.teamsparker.android.data.remote.repository

import com.teamsparker.android.data.remote.entity.response.BaseResponse
import com.teamsparker.android.data.remote.entity.response.RefreshResponse

interface RefreshRepository {

    suspend fun getRefresh(roomId:Int): Result<BaseResponse<RefreshResponse>>
}
