package com.teamsparker.android.data.remote.service

import com.teamsparker.android.data.remote.entity.response.BaseResponse
import com.teamsparker.android.data.remote.entity.response.NoDataResponse
import com.teamsparker.android.data.remote.entity.response.ProfileResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.Part
import retrofit2.http.PartMap

interface ProfileService {
    @GET("user/profile")
    suspend fun getProfile(): BaseResponse<ProfileResponse>

    @Multipart
    @PATCH("user/profile")
    suspend fun patchProfileWithImg(
        @PartMap map: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part profileImg: MultipartBody.Part
    ): NoDataResponse

    @Multipart
    @PATCH("user/profile")
    suspend fun patchProfile(
        @PartMap map: Map<String, @JvmSuppressWildcards RequestBody>
    ): NoDataResponse
}
