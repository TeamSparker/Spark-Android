package com.spark.android.data.remote.service

import com.spark.android.data.remote.entity.request.SetPurposeRequest
import com.spark.android.data.remote.entity.response.SetPurposeResponse
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.Path

interface SetPurposeService {

    @PATCH("room/{roomId}/purpose")
    suspend fun setPurpose(
        @Path("roomId") roomId : Int,
        @Body body : SetPurposeRequest
    ): SetPurposeResponse
}