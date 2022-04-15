package com.spark.android.data.local.datasource

import android.content.SharedPreferences
import com.spark.android.data.remote.entity.response.AlarmSettingResponse
import com.spark.android.ui.alarmsetting.AlarmSettingViewModel.Companion.ALARM_CERTIFICATION
import com.spark.android.ui.alarmsetting.AlarmSettingViewModel.Companion.ALARM_CONSIDER
import com.spark.android.ui.alarmsetting.AlarmSettingViewModel.Companion.ALARM_REMIND
import com.spark.android.ui.alarmsetting.AlarmSettingViewModel.Companion.ALARM_ROOM_START
import com.spark.android.ui.alarmsetting.AlarmSettingViewModel.Companion.ALARM_SPARK
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

    override fun saveAlarmSettingLocalSaved(isSaved: Boolean) {
        localPreferences.edit()
            .putBoolean(ALARM_LOCAL_SAVED, isSaved)
            .apply()
    }

    override fun saveAlarmSettingValue(alarmSettingValue: AlarmSettingResponse) {
        localPreferences.edit()
            .putBoolean(ALARM_ROOM_START, alarmSettingValue.roomStart)
            .putBoolean(ALARM_SPARK, alarmSettingValue.spark)
            .putBoolean(ALARM_CONSIDER, alarmSettingValue.consider)
            .putBoolean(ALARM_CERTIFICATION, alarmSettingValue.certification)
            .putBoolean(ALARM_REMIND, alarmSettingValue.remind)
            .apply()
    }

    override fun patchAlarmSettingValue(category: String) {
        when (category) {
            ALARM_ROOM_START -> localPreferences.edit().putBoolean(
                ALARM_ROOM_START,
                !localPreferences.getBoolean(ALARM_ROOM_START, true)
            ).apply()
            ALARM_SPARK -> localPreferences.edit().putBoolean(
                ALARM_SPARK,
                !localPreferences.getBoolean(ALARM_SPARK, true)
            ).apply()
            ALARM_CONSIDER -> localPreferences.edit().putBoolean(
                ALARM_CONSIDER,
                !localPreferences.getBoolean(ALARM_CONSIDER, true)
            ).apply()
            ALARM_CERTIFICATION -> localPreferences.edit().putBoolean(
                ALARM_CERTIFICATION,
                !localPreferences.getBoolean(ALARM_CERTIFICATION, true)
            ).apply()
            ALARM_REMIND -> localPreferences.edit().putBoolean(
                ALARM_REMIND,
                !localPreferences.getBoolean(ALARM_REMIND, true)
            ).apply()
        }
    }

    override fun getAccessToken() =
        localPreferences.getString(ACCESS_TOKEN, DEFAULT_STRING_VALUE) ?: DEFAULT_STRING_VALUE

    override fun getUserKakaoUserId(): String =
        localPreferences.getString(USER_KAKAO_USER_ID, DEFAULT_STRING_VALUE) ?: DEFAULT_STRING_VALUE

    override fun getUserNickname(): String =
        localPreferences.getString(USER_NICKNAME, DEFAULT_STRING_VALUE) ?: DEFAULT_STRING_VALUE

    override fun getAlarmSettingLocalSaved(): Boolean =
        localPreferences.getBoolean(ALARM_LOCAL_SAVED, false)

    override fun getAlarmSettingValue(): AlarmSettingResponse =
        AlarmSettingResponse(
            roomStart = localPreferences.getBoolean(ALARM_ROOM_START, true),
            spark = localPreferences.getBoolean(ALARM_SPARK, true),
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
        const val DEFAULT_STRING_VALUE = ""
    }
}
