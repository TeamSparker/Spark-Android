package com.spark.android.data.remote.service

import com.spark.android.data.remote.entity.request.CertifyRequest
import com.spark.android.data.remote.entity.response.BaseResponse
import com.spark.android.data.remote.entity.response.CertifyResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface CertifyService {
    @POST("room/{roomId}/record")
    suspend fun postCertification(
        @Path("roomId") roomId : Int,
        @Body body: CertifyRequest
    ): BaseResponse<CertifyResponse>
}