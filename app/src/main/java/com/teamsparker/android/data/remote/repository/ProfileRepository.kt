package com.teamsparker.android.data.remote.repository

import com.teamsparker.android.data.remote.entity.response.BaseResponse
import com.teamsparker.android.data.remote.entity.response.NoDataResponse
import com.teamsparker.android.data.remote.entity.response.ProfileResponse
import okhttp3.MultipartBody

interface ProfileRepository {
    suspend fun getProfile(): Result<BaseResponse<ProfileResponse>>

    suspend fun patchProfile(
        nickname: String,
        profileImg: MultipartBody.Part?
    ): Result<NoDataResponse>

    fun setSignUpHabitUserGuideState(state: Boolean)
}
