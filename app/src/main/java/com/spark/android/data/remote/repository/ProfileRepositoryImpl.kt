package com.spark.android.data.remote.repository

import com.spark.android.data.remote.datasource.ProfileDataSource
import com.spark.android.data.remote.entity.response.BaseResponse
import com.spark.android.data.remote.entity.response.NoDataResponse
import com.spark.android.data.remote.entity.response.ProfileResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val profileDataSource: ProfileDataSource
) : ProfileRepository {
    override suspend fun getProfile(): Result<BaseResponse<ProfileResponse>> =
        kotlin.runCatching { profileDataSource.getProfile() }

    override suspend fun patchProfile(
        nickname: String,
        profileImg: MultipartBody.Part?
    ): Result<NoDataResponse> =
        kotlin.runCatching {
            val map = mutableMapOf<String, @JvmSuppressWildcards RequestBody>()
            map["nickname"] = nickname.toRequestBody("text/plain".toMediaTypeOrNull())
            profileDataSource.patchProfile(map, profileImg)
        }
}
