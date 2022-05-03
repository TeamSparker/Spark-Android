package com.teamsparker.android.data.remote.datasource

import com.teamsparker.android.data.remote.entity.response.BaseResponse
import com.teamsparker.android.data.remote.entity.response.NoDataResponse
import com.teamsparker.android.data.remote.entity.response.ProfileResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface ProfileDataSource {
    suspend fun getProfile(): BaseResponse<ProfileResponse>

    suspend fun patchProfile(
        map: Map<String, RequestBody>,
        profileImg: MultipartBody.Part?
    ): NoDataResponse
}
