package com.teamsparker.android.data.remote.repository

import com.teamsparker.android.data.remote.entity.request.SetPurposeRequest
import com.teamsparker.android.data.remote.entity.response.NoDataResponse

interface SetPurposeRepository {

    suspend fun setPurpose(roomId:Int,body:SetPurposeRequest): Result<NoDataResponse>
}
