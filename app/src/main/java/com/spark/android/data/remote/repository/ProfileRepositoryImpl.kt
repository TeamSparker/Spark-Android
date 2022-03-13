package com.spark.android.data.remote.repository

import com.spark.android.data.remote.datasource.ProfileDataSource
import com.spark.android.data.remote.entity.response.BaseResponse
import com.spark.android.data.remote.entity.response.ProfileResponse
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val profileDataSource: ProfileDataSource
) : ProfileRepository {
    override suspend fun getProfile(): Result<BaseResponse<ProfileResponse>> =
        kotlin.runCatching { profileDataSource.getProfile() }
}
