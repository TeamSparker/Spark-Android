package com.teamsparker.android.data.local.datasource

interface LocalPreferencesHabitDataSource {
    fun setHabitUserGuideState(state: Boolean)
    fun getHabitUserGuideState(): Boolean
}
