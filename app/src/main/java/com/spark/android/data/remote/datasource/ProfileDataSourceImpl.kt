package com.spark.android.data.remote.datasource

import com.spark.android.data.remote.entity.response.BaseResponse
import com.spark.android.data.remote.entity.response.NoDataResponse
import com.spark.android.data.remote.entity.response.ProfileResponse
import com.spark.android.data.remote.service.ProfileService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class ProfileDataSourceImpl @Inject constructor(
    private val profileService: ProfileService
) : ProfileDataSource {
    override suspend fun getProfile(): BaseResponse<ProfileResponse> =
        profileService.getProfile()

    override suspend fun patchProfile(
        map: Map<String, RequestBody>,
        profileImg: MultipartBody.Part?
    ): NoDataResponse =
        if (profileImg == null) {
            profileService.patchProfile(map)
        } else {
            profileService.patchProfileWithImg(map, profileImg)
        }
}
