package com.spark.android.data.remote.datasource

import com.spark.android.data.remote.entity.response.BaseResponse
import com.spark.android.data.remote.entity.response.HomeResponse
import com.spark.android.data.remote.entity.response.NoDataResponse
import com.spark.android.data.remote.entity.response.WaitingRoomInfoResponse
import com.spark.android.data.remote.service.HomeHabitRoomFinishService
import com.spark.android.data.remote.service.HomeService
import javax.inject.Inject

class RemoteHomeDataSourceImpl @Inject constructor(
    private val homeHabitRoomFinishService: HomeHabitRoomFinishService,
    private val homeService: HomeService
) : RemoteHomeDataSource {
    override suspend fun getHomeAllRoom(lastId: Int, size: Int): BaseResponse<HomeResponse> =
        homeService.getHomeAllRoom(lastId, size)

    override suspend fun readFinishHabitRoom(roomId: Int): NoDataResponse =
        homeHabitRoomFinishService.readFinishHabitRoom(roomId)
}