package com.teamsparker.android.data.remote.service

import com.teamsparker.android.data.remote.entity.response.BaseResponse
import com.teamsparker.android.data.remote.entity.response.CertifyResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path

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
