package com.spark.android.data.remote.datasource

import com.spark.android.data.remote.entity.response.SignUpResponse
import com.spark.android.data.remote.service.AuthService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authService: AuthService
) : AuthDataSource {
    override suspend fun postSignUp(
        map: Map<String, RequestBody>,
        profileImg: MultipartBody.Part?
    ): SignUpResponse {
        return if (profileImg == null) {
            authService.postSignUp(map)
        } else {
            authService.postSignUpWithImg(map, profileImg)
        }
    }
}
