package com.spark.android.data.remote.datasource

import com.spark.android.data.remote.entity.response.BaseResponse
import com.spark.android.data.remote.entity.response.ProfileResponse
import com.spark.android.data.remote.service.ProfileService
import javax.inject.Inject

class ProfileDataSourceImpl @Inject constructor(
    private val profileService: ProfileService
) : ProfileDataSource {
    override suspend fun getProfile(): BaseResponse<ProfileResponse> =
        profileService.getProfile()
}
