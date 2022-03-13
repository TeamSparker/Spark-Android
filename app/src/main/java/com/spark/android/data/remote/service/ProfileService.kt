package com.spark.android.data.remote.service

import com.spark.android.data.remote.entity.response.BaseResponse
import com.spark.android.data.remote.entity.response.NoDataResponse
import com.spark.android.data.remote.entity.response.ProfileResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Part
import retrofit2.http.PartMap

interface ProfileService {
    @GET("user/profile")
    suspend fun getProfile(): BaseResponse<ProfileResponse>

    @PATCH("user/profile")
    suspend fun patchProfileWithImg(
        @PartMap map: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part profileImg: MultipartBody.Part
    ): NoDataResponse

    @PATCH("user/profile")
    suspend fun patchProfile(
        @PartMap map: Map<String, @JvmSuppressWildcards RequestBody>
    ): NoDataResponse
}
