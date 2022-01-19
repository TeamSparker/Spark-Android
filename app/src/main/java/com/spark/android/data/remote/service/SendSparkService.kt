package com.spark.android.data.remote.service

import com.spark.android.data.remote.entity.request.SendSparkRequest
import com.spark.android.data.remote.entity.response.NoDataResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface SendSparkService {
    @POST("room/{roomId}/spark")
    suspend fun sendSpark(
        @Path("roomId") roomId: Int,
        @Body body: SendSparkRequest,
    ): NoDataResponse
}