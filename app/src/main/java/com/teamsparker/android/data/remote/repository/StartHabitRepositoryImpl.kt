package com.teamsparker.android.data.remote.repository

import com.teamsparker.android.data.remote.service.StartHabitService
import javax.inject.Inject

class StartHabitRepositoryImpl @Inject constructor(
    private val startHabitService: StartHabitService
) :StartHabitRepository{
    override suspend fun startHabit(
        roomId:Int
    )= kotlin.runCatching {
        startHabitService.startHabit(roomId)
    }
}
