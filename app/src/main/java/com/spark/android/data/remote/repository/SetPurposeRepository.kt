package com.spark.android.data.remote.repository

import com.spark.android.data.remote.entity.request.SetPurposeRequest
import com.spark.android.data.remote.entity.response.SetPurposeResponse

interface SetPurposeRepository {

    suspend fun setPurpose(roomId:Int,body:SetPurposeRequest): SetPurposeResponse
}