package com.spark.android.data.remote.repository

import com.spark.android.data.local.datasource.LocalPreferencesHabitDataSource
import javax.inject.Inject

class HabitRepositoryImpl @Inject constructor(
    private val localPreferencesHabitDataSource: LocalPreferencesHabitDataSource
) : HabitRepository {

    override fun setHabitUserGuideState(state: Boolean) {
        localPreferencesHabitDataSource.setHabitUserGuideState(state)
    }

    override fun getHabitUserGuideState(): Boolean =
        localPreferencesHabitDataSource.getHabitUserGuideState()
}