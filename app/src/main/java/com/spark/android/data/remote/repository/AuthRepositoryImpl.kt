package com.spark.android.data.remote.repository

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.user.UserApiClient
import com.spark.android.data.local.datasource.LocalPreferencesDataSource
import com.spark.android.data.remote.datasource.AuthDataSource
import com.spark.android.data.remote.entity.response.BaseResponse
import com.spark.android.data.remote.entity.response.DoorbellResponse
import com.spark.android.data.remote.entity.response.NoDataResponse
import com.spark.android.ui.intro.VersionUpdateState
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import timber.log.Timber
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val localPreferencesDataSource: LocalPreferencesDataSource
) : AuthRepository {
    override fun initKakaoUserId(initId: (String) -> Unit) {
        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {
                Timber.tag("kakao").e(error, "토큰 정보 보기 실패")
            } else if (tokenInfo != null) {
                localPreferencesDataSource.saveUserKakakoUserId("Kakao@${requireNotNull(tokenInfo.id)}")
                initId("Kakao@${requireNotNull(tokenInfo.id)}")
                Timber.tag("kakao").d(
                    "토큰 정보 보기 성공\n회원번호: ${tokenInfo.id}\n만료시간: ${tokenInfo.expiresIn} 초"
                )
            }
        }
    }

    override fun unLinkKakaoAccount(initSuccessWithdraw: (Boolean) -> Unit) {
        UserApiClient.instance.unlink { error ->
            if (error != null) {
                Timber.tag("kakao").e(error, "연결 끊기 실패")
                initSuccessWithdraw(false)
            } else {
                Timber.tag("kakao").d("연결 끊기 성공. SDK에서 토큰 삭제 됨")
                initSuccessWithdraw(true)
            }
        }
    }

    override fun getFcmToken(getFcmToken: (String) -> Unit) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            getFcmToken(requireNotNull(task.result))
        })
    }

    override fun saveAccessToken(accessToken: String) {
        localPreferencesDataSource.saveAccessToken(accessToken)
    }

    override fun removeAccessToken() {
        localPreferencesDataSource.removeAccessToken()
    }

    override fun removeKakaoUserId() {
        localPreferencesDataSource.removeKakaoUserId()
    }

    override fun versionCheck(storeVersion: String, currentVersion: String): VersionUpdateState {
        val storeVersionSplit = storeVersion.split(".").map { it.toInt() }
        val currentVersionSplit = currentVersion.split(".").map { it.toInt() }
        return when {
            storeVersionSplit[0] > currentVersionSplit[0] -> {
                VersionUpdateState.FORCE
            }
            storeVersionSplit[1] > currentVersionSplit[1] -> {
                VersionUpdateState.FORCE
            }
            else -> {
                VersionUpdateState.OPTIONAL
            }
        }
    }

    override suspend fun getStoreVersion() = kotlin.runCatching {
        authDataSource.getStoreVersion()
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

    override suspend fun getAccessToken(
        socialId: String,
        fcmToken: String
    ): Result<BaseResponse<DoorbellResponse>> =
        kotlin.runCatching { authDataSource.getAccessToken(socialId, fcmToken) }

    override suspend fun postSignOut(): Result<NoDataResponse> =
        kotlin.runCatching { authDataSource.postSingOut() }

    override suspend fun deleteUser(): Result<NoDataResponse> =
        kotlin.runCatching { authDataSource.deleteUser() }
}
