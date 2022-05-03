package com.teamsparker.android.data.local.datasource

import android.content.SharedPreferences

import javax.inject.Inject

class LocalPreferencesHabitDataSourceImpl @Inject constructor(
    private val localPreferences: SharedPreferences
) : LocalPreferencesHabitDataSource {
    override fun setHabitUserGuideState(state: Boolean) {
        localPreferences.edit().putBoolean(USER_GUIDE_DIALOG_STATE, state).apply()
    }

    override fun getHabitUserGuideState(): Boolean =
        localPreferences.getBoolean(USER_GUIDE_DIALOG_STATE, true)


    companion object {
        const val USER_GUIDE_DIALOG_STATE = "USER_GUIDE_DIALOG_STATE"
    }
}
