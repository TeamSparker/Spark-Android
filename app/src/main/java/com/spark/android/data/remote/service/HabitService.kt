package com.spark.android.data.remote.service

import com.spark.android.data.remote.entity.response.BaseResponse
import com.spark.android.data.remote.entity.response.HabitResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface HabitService {
    @GET("room/{roomId}")
    suspend fun getHabitRoom(
        @Path("roomId") roomId : Int
    ):BaseResponse<HabitResponse>
}
