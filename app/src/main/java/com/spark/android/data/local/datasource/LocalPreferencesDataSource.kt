package com.spark.android.data.local.datasource

import com.spark.android.ui.alarmsetting.AlarmOnOff

interface LocalPreferencesDataSource {
    fun saveAccessToken(accessToken: String)

    fun saveUserKakakoUserId(kakaoUserId: String)

    fun saveUserNickname(userNickname: String)

    fun saveAlarmSettingLocalSaved(received: Boolean)

    fun saveAlarmSettingValue(
        startHabit: Boolean = true,
        sendSpark: Boolean = true,
        consider: Boolean = true,
        certification: Boolean = true,
        remind: Boolean = true
    )

    fun getAccessToken(): String

    fun getUserKakaoUserId(): String

    fun getUserNickname(): String

    fun getAlarmSettingLocalSaved(): Boolean

    fun getAlarmSettingValue(): AlarmOnOff

    fun removeAccessToken()

    fun removeKakaoUserId()
}
