package com.spark.android.data.remote.datasource

import com.spark.android.data.remote.entity.response.BaseResponse
import com.spark.android.data.remote.entity.response.DoorbellResponse
import com.spark.android.data.remote.entity.response.NoDataResponse
import com.spark.android.data.remote.entity.response.SignUpResponse
import com.spark.android.data.remote.entity.response.VersionResponse
import com.spark.android.data.remote.service.AuthService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authService: AuthService
) : AuthDataSource {
    override suspend fun getStoreVersion(): BaseResponse<VersionResponse> =
        BaseResponse(
            data = VersionResponse("1.0.0"),
            message = "성공",
            status = 200,
            success = true
        )

    override suspend fun postSignUp(
        map: Map<String, RequestBody>,
        profileImg: MultipartBody.Part?
    ): BaseResponse<SignUpResponse> =
        if (profileImg == null) {
            authService.postSignUp(map)
        } else {
            authService.postSignUpWithImg(map, profileImg)
        }

    override suspend fun getAccessToken(
        socialId: String,
        fcmToken: String
    ): BaseResponse<DoorbellResponse> =
        authService.getAccessToken(socialId, fcmToken)

    override suspend fun postSingOut(): NoDataResponse = authService.postSingOut()

    override suspend fun deleteUser(): NoDataResponse = authService.deleteUser()
}
