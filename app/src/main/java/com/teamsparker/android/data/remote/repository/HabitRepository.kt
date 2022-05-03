package com.teamsparker.android.data.remote.repository

interface HabitRepository {

    fun setHabitUserGuideState(state: Boolean)

    fun getHabitUserGuideState(): Boolean
}
