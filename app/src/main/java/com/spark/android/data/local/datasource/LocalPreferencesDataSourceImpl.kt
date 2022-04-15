package com.spark.android.data.local.datasource

import android.content.SharedPreferences
import com.spark.android.ui.alarmsetting.AlarmOnOff
import javax.inject.Inject

class LocalPreferencesDataSourceImpl @Inject constructor(
    private val localPreferences: SharedPreferences
) : LocalPreferencesDataSource {
    override fun saveAccessToken(accessToken: String) {
        localPreferences.edit()
            .putString(ACCESS_TOKEN, accessToken)
            .apply()
    }

    override fun saveUserKakakoUserId(kakaoUserId: String) {
        localPreferences.edit()
            .putString(USER_KAKAO_USER_ID, kakaoUserId)
            .apply()
    }

    override fun saveUserNickname(userNickname: String) {
        localPreferences.edit()
            .putString(USER_NICKNAME, userNickname)
            .apply()
    }

    override fun saveAlarmSettingLocalSaved(received: Boolean) {
        localPreferences.edit()
            .putBoolean(ALARM_LOCAL_SAVED, received)
            .apply()
    }

    override fun saveAlarmSettingValue(
        startHabit: Boolean,
        sendSpark: Boolean,
        consider: Boolean,
        certification: Boolean,
        remind: Boolean
    ) {
        localPreferences.edit()
            .putBoolean(ALARM_START_HABIT, startHabit)
            .putBoolean(ALARM_SEND_SPARK, sendSpark)
            .putBoolean(ALARM_CONSIDER, consider)
            .putBoolean(ALARM_CERTIFICATION, certification)
            .putBoolean(ALARM_REMIND, remind)
            .apply()
    }

    override fun getAccessToken() =
        localPreferences.getString(ACCESS_TOKEN, DEFAULT_STRING_VALUE) ?: DEFAULT_STRING_VALUE

    override fun getUserKakaoUserId(): String =
        localPreferences.getString(USER_KAKAO_USER_ID, DEFAULT_STRING_VALUE) ?: DEFAULT_STRING_VALUE

    override fun getUserNickname(): String =
        localPreferences.getString(USER_NICKNAME, DEFAULT_STRING_VALUE) ?: DEFAULT_STRING_VALUE

    override fun getAlarmSettingLocalSaved(): Boolean =
        localPreferences.getBoolean(ALARM_LOCAL_SAVED, false)

    override fun getAlarmSettingValue(): AlarmOnOff =
        AlarmOnOff(
            startHabit = localPreferences.getBoolean(ALARM_START_HABIT, true),
            sendSpark = localPreferences.getBoolean(ALARM_SEND_SPARK, true),
            consider = localPreferences.getBoolean(ALARM_CONSIDER, true),
            certification = localPreferences.getBoolean(ALARM_CERTIFICATION, true),
            remind = localPreferences.getBoolean(ALARM_REMIND, true)
        )

    override fun removeAccessToken() {
        localPreferences.edit()
            .remove(ACCESS_TOKEN)
            .apply()
    }

    override fun removeKakaoUserId() {
        localPreferences.edit()
            .remove(USER_KAKAO_USER_ID)
            .apply()
    }

    companion object {
        private const val ACCESS_TOKEN = "ACCESS_TOKEN"
        private const val USER_KAKAO_USER_ID = "USER_KAKAO_USER_ID"
        private const val USER_NICKNAME = "USER_NAME"
        private const val ALARM_LOCAL_SAVED = "ALARM_LOCAL_SAVED"
        private const val ALARM_START_HABIT = "ALARM_START_HABIT"
        private const val ALARM_SEND_SPARK = "ALARM_SEND_SPARK"
        private const val ALARM_CONSIDER = "ALARM_CONSIDER"
        private const val ALARM_CERTIFICATION = "ALARM_CERTIFICATION"
        private const val ALARM_REMIND = "ALARM_REMIND"
        const val DEFAULT_STRING_VALUE = ""
    }
}
