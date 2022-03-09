package com.spark.android.data.local.datasource

import android.content.SharedPreferences
import javax.inject.Inject

class LocalPreferencesWaitingRoomDataSourceImpl @Inject constructor(
    private val localPreferences: SharedPreferences
) : LocalPreferencesWaitingRoomDataSource {



    override fun setHomeToastMessage(message: String) {
        localPreferences.edit().putString(HOME_TOAST_MESSAGE,message).apply()
    }

    override fun setHomeToastMessageState(state: Boolean) {
        localPreferences.edit().putBoolean(HOME_TOAST_MESSAGE_STATE,state).apply()
    }

    companion object {
        const val HOME_TOAST_MESSAGE = "HOME_TOAST_MESSAGE"
        const val HOME_TOAST_MESSAGE_STATE = "HOME_TOAST_MESSAGE_STATE"
    }
}