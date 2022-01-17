package com.spark.android.data.remote

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

object LocalPreferences {
    private const val ACCESS_TOKEN = "ACCESS_TOKEN"
    private const val USER_KAKAO_USER_ID = "USER_KAKAO_USER_ID"
    private const val USER_NICKNAME = "USER_NAME"

    private lateinit var localPreferences: SharedPreferences

    fun init(context: Context) {
        localPreferences = EncryptedSharedPreferences.create(
            context,
            context.packageName,
            MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun saveAccessToken(accessToken: String) {
        localPreferences.edit()
            .putString(ACCESS_TOKEN, accessToken)
            .apply()
    }

    fun saveUserKakakoUserId(kakaoUserId: Long) {
        localPreferences.edit()
            .putLong(USER_KAKAO_USER_ID, kakaoUserId)
            .apply()
    }

    fun saveUserNickname(userNickname: String) {
        localPreferences.edit()
            .putString(USER_NICKNAME, userNickname)
            .apply()
    }

    fun getAccessToken() =
        localPreferences.getString(ACCESS_TOKEN, "") ?: ""

    fun getUserKakaoUserId(): Long =
        localPreferences.getLong(USER_KAKAO_USER_ID, -1L)

    fun getUserNickname(): String =
        localPreferences.getString(USER_NICKNAME, "") ?: ""
}
