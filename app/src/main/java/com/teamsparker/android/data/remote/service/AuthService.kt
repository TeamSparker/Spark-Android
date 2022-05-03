package com.teamsparker.android.data.remote.service

import com.teamsparker.android.data.remote.entity.response.BaseResponse
import com.teamsparker.android.data.remote.entity.response.DoorbellResponse
import com.teamsparker.android.data.remote.entity.response.NoDataResponse
import com.teamsparker.android.data.remote.entity.response.SignUpResponse
import com.teamsparker.android.data.remote.entity.response.VersionResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Query

interface AuthService {
    @Multipart
    @POST("auth/signup")
    suspend fun postSignUpWithImg(
        @PartMap map: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part profileImg: MultipartBody.Part
    ): BaseResponse<SignUpResponse>

    @Multipart
    @POST("auth/signup")
    suspend fun postSignUp(
        @PartMap map: Map<String, @JvmSuppressWildcards RequestBody>
    ): BaseResponse<SignUpResponse>

    @GET("auth/doorbell")
    suspend fun getAccessToken(
        @Query("socialId") socialId: String,
        @Query("fcmToken") fcmToken: String
    ): BaseResponse<DoorbellResponse>

    @POST("auth/signout")
    suspend fun postSingOut(): NoDataResponse

    @DELETE("auth/user")
    suspend fun deleteUser(): NoDataResponse

    @GET("version")
    suspend fun getStoreVersion(): BaseResponse<VersionResponse>
}
