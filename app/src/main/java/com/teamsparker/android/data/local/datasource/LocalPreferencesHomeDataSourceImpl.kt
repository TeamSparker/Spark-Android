package com.teamsparker.android.data.local.datasource

import android.content.SharedPreferences
import javax.inject.Inject

class LocalPreferencesHomeDataSourceImpl @Inject constructor(
    private val localPreferences:SharedPreferences
) :LocalPreferencesHomeDataSource {

    override fun getHomeToastMessage(): String =
        localPreferences.getString(LocalPreferencesWaitingRoomDataSourceImpl.HOME_TOAST_MESSAGE, "") ?: ""


    override fun getHomeToastMessageState(): Boolean =
        localPreferences.getBoolean(LocalPreferencesWaitingRoomDataSourceImpl.HOME_TOAST_MESSAGE_STATE, false)

    override fun setHomeToastMessage(message: String) {
        localPreferences.edit().putString(LocalPreferencesWaitingRoomDataSourceImpl.HOME_TOAST_MESSAGE,message).apply()
    }

    override fun setHomeToastMessageState(state: Boolean) {
        localPreferences.edit().putBoolean(LocalPreferencesWaitingRoomDataSourceImpl.HOME_TOAST_MESSAGE_STATE,state).apply()
    }

}
