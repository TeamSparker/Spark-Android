package com.spark.android.data.remote.repository

import com.spark.android.data.remote.entity.response.NoDataResponse

interface StartHabitRepository {

    suspend fun startHabit(roomId: Int): Result<NoDataResponse>
}