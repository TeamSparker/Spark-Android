package com.spark.android.data.remote.repository

import com.spark.android.data.remote.entity.response.NoDataResponse
import com.spark.android.data.remote.service.StartHabitService
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