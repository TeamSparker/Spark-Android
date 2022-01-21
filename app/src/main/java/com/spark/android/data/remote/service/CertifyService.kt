package com.spark.android.data.remote.service

import com.spark.android.data.remote.entity.response.BaseResponse
import com.spark.android.data.remote.entity.response.CertifyResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface CertifyService {
    @Multipart
    @POST("room/{roomId}/record")
    suspend fun postCertificationFromStart(
        @Path("roomId") roomId: Int,
        @PartMap map: Map<String, @JvmSuppressWildcards RequestBody>?,
        @Part img: MultipartBody.Part,
    ): BaseResponse<CertifyResponse>

    @Multipart
    @POST("room/{roomId}/record")
    suspend fun postCertification(
        @Path("roomId") roomId: Int,
        @Part img: MultipartBody.Part,
    ): BaseResponse<CertifyResponse>
}