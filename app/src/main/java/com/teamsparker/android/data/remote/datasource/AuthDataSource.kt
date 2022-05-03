package com.teamsparker.android.data.remote.datasource

import com.teamsparker.android.data.remote.entity.response.BaseResponse
import com.teamsparker.android.data.remote.entity.response.DoorbellResponse
import com.teamsparker.android.data.remote.entity.response.NoDataResponse
import com.teamsparker.android.data.remote.entity.response.SignUpResponse
import com.teamsparker.android.data.remote.entity.response.VersionResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface AuthDataSource {
    suspend fun getStoreVersion(): BaseResponse<VersionResponse>

    suspend fun postSignUp(
        map: Map<String, RequestBody>,
        profileImg: MultipartBody.Part?
    ): BaseResponse<SignUpResponse>

    suspend fun getAccessToken(socialId: String, fcmToken: String): BaseResponse<DoorbellResponse>

    suspend fun postSingOut(): NoDataResponse

    suspend fun deleteUser(): NoDataResponse
}
