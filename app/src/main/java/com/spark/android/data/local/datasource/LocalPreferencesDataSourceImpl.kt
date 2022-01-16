package com.spark.android.data.local.datasource

import android.content.SharedPreferences
import javax.inject.Inject

class LocalPreferencesDataSourceImpl @Inject constructor(
    private val localPreferences: SharedPreferences
) : LocalPreferencesDataSource {
    override fun saveAccessToken(accessToken: String) {
        localPreferences.edit()
            .putString(ACCESS_TOKEN, accessToken)
            .apply()
    }

    override fun saveUserKakakoUserId(kakaoUserId: Long) {
        localPreferences.edit()
            .putLong(USER_KAKAO_USER_ID, kakaoUserId)
            .apply()
    }

    override fun saveUserNickname(userNickname: String) {
        localPreferences.edit()
            .putString(USER_NICKNAME, userNickname)
            .apply()
    }

    override fun getAccessToken() =
        localPreferences.getString(ACCESS_TOKEN, "") ?: ""

    override fun getUserKakaoUserId(): Long =
        localPreferences.getLong(USER_KAKAO_USER_ID, -1L)

    override fun getUserNickname(): String =
        localPreferences.getString(USER_NICKNAME, "") ?: ""


    companion object {
        private const val ACCESS_TOKEN = "ACCESS_TOKEN"
        private const val USER_KAKAO_USER_ID = "USER_KAKAO_USER_ID"
        private const val USER_NICKNAME = "USER_NAME"
    }
}
