package com.spark.android.data.local.datasource

interface LocalPreferencesHabitDataSource {

    fun setHabitUserGuideState(state: Boolean)
    fun getHabitUserGuideState(): Boolean
}