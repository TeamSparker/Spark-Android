package com.teamsparker.android.data.remote.repository

import com.teamsparker.android.data.remote.entity.response.NoDataResponse

interface StartHabitRepository {

    suspend fun startHabit(roomId: Int): Result<NoDataResponse>
}
