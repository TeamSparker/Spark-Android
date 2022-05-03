package com.teamsparker.android.data.local.datasource

import com.teamsparker.android.data.remote.entity.response.AlarmSettingResponse

interface LocalPreferencesDataSource {
    fun saveAccessToken(accessToken: String)

    fun saveUserKakakoUserId(kakaoUserId: String)

    fun saveUserNickname(userNickname: String)

    fun saveAlarmSettingLocalSaved(isSaved: Boolean)

    fun saveAlarmSettingValue(alarmSettingValue: AlarmSettingResponse)

    fun patchAlarmSettingValue(category:String)

    fun getAccessToken(): String

    fun getUserKakaoUserId(): String

    fun getUserNickname(): String

    fun getAlarmSettingLocalSaved(): Boolean

    fun getAlarmSettingValue(): AlarmSettingResponse

    fun removeAccessToken()

    fun removeKakaoUserId()
}
