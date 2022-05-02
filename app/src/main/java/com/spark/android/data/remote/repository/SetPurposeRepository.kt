package com.spark.android.data.remote.repository

import com.spark.android.data.remote.entity.request.SetPurposeRequest
import com.spark.android.data.remote.entity.response.NoDataResponse

interface SetPurposeRepository {

    suspend fun setPurpose(roomId:Int,body:SetPurposeRequest): Result<NoDataResponse>
}
