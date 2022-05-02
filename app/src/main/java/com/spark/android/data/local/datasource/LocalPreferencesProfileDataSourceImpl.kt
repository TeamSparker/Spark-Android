package com.spark.android.data.local.datasource

import android.content.SharedPreferences
import javax.inject.Inject

class LocalPreferencesProfileDataSourceImpl @Inject constructor(
    private val localPreferences: SharedPreferences
) :LocalPreferencesProfileDataSource {

    override fun setSignUpHabitUserGuideState(state: Boolean) {
        localPreferences.edit().putBoolean(LocalPreferencesHabitDataSourceImpl.USER_GUIDE_DIALOG_STATE,state).apply()
    }
}
