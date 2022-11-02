package com.teamsparker.android.data.remote.repository

import com.teamsparker.android.data.remote.entity.response.HabitRoomTimeLine

interface HabitRepository {

    fun setHabitUserGuideState(state: Boolean)

    fun getHabitUserGuideState(): Boolean

    suspend fun getHabitRoomTimeLine(roomId: Int): Result<HabitRoomTimeLine>
}
