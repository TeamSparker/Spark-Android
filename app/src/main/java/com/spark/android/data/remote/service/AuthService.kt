package com.spark.android.data.remote.service

import com.spark.android.data.remote.entity.request.SignUpRequest
import com.spark.android.data.remote.entity.response.SignUpResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface AuthService {
    @Multipart
    @POST("auth/signup")
    suspend fun postSignUpWithImg(
        @PartMap map: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part profileImg: MultipartBody.Part
    ): SignUpResponse

    @Multipart
    @POST("auth/signup")
    suspend fun postSignUp(
        @PartMap map: Map<String, @JvmSuppressWildcards RequestBody>
    ): SignUpResponse
}
