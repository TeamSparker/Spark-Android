package com.spark.android.data.remote.repository

import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.user.UserApiClient
import com.spark.android.data.local.datasource.LocalPreferencesDataSource
import com.spark.android.data.remote.datasource.AuthDataSource
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val localPreferencesDataSource: LocalPreferencesDataSource
) : AuthRepository {
    override fun initKakaoUserId(initId: (String) -> Unit) {
        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {
                Log.e("kakao", "토큰 정보 보기 실패", error)
            } else if (tokenInfo != null) {
                initId("Kakao@${requireNotNull(tokenInfo.id)}")
                Log.d(
                    "kakao", "토큰 정보 보기 성공" +
                            "\n회원번호: ${tokenInfo.id}" +
                            "\n만료시간: ${tokenInfo.expiresIn} 초"
                )
            }
        }
    }

    override fun getFcmToken(getFcmToken: (String) -> Unit) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            getFcmToken(requireNotNull(task.result))
        })
    }

    override suspend fun postSignUp(
        nickname: String,
        kakaoUserId: String,
        fcmToken: String,
        profileImg: MultipartBody.Part?
    ) = kotlin.runCatching {
        val map = mutableMapOf<String, @JvmSuppressWildcards RequestBody>()
        map["nickname"] = nickname.toRequestBody("text/plain".toMediaTypeOrNull())
        map["socialId"] = kakaoUserId.toRequestBody("text/plain".toMediaTypeOrNull())
        map["fcmToken"] = fcmToken.toRequestBody("text/plain".toMediaTypeOrNull())
        authDataSource.postSignUp(map, profileImg)
    }

    override fun saveAccessToken(accessToken: String) {
        localPreferencesDataSource.saveAccessToken(accessToken)
    }
}
