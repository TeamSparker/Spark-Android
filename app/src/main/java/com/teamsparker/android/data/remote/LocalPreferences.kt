package com.teamsparker.android.data.remote

import android.content.Context
import android.content.SharedPreferences
import com.teamsparker.android.data.local.datasource.LocalPreferencesWaitingRoomDataSourceImpl.Companion.HOME_TOAST_MESSAGE
import com.teamsparker.android.data.local.datasource.LocalPreferencesWaitingRoomDataSourceImpl.Companion.HOME_TOAST_MESSAGE_STATE

object LocalPreferences {
    private const val ACCESS_TOKEN = "ACCESS_TOKEN"
    private const val USER_KAKAO_USER_ID = "USER_KAKAO_USER_ID"
    private const val USER_NICKNAME = "USER_NAME"

    private lateinit var localPreferences: SharedPreferences

    fun init(context: Context) {
        localPreferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
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

    fun setExitHabitRoomHomeToastMessage(toastMessage: String) {
        localPreferences.edit()
            .putString(HOME_TOAST_MESSAGE, toastMessage)
            .apply()
    }

    fun setExitHabitRoomHomeToastMessageState(state: Boolean) {
        localPreferences.edit()
            .putBoolean(HOME_TOAST_MESSAGE_STATE, state)
            .apply()
    }

    fun getAccessToken() =
        localPreferences.getString(ACCESS_TOKEN, "") ?: ""

    fun getUserKakaoUserId(): Long =
        localPreferences.getLong(USER_KAKAO_USER_ID, -1L)

    fun getUserNickname(): String =
        localPreferences.getString(USER_NICKNAME, "") ?: ""
}
