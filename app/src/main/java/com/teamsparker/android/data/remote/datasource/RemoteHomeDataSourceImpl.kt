package com.teamsparker.android.data.remote.datasource

import com.teamsparker.android.data.remote.entity.response.*
import com.teamsparker.android.data.remote.service.HomeHabitRoomFinishService
import com.teamsparker.android.data.remote.service.HomeNoticeRedDotService
import com.teamsparker.android.data.remote.service.HomeService
import javax.inject.Inject

class RemoteHomeDataSourceImpl @Inject constructor(
    private val homeHabitRoomFinishService: HomeHabitRoomFinishService,
    private val homeService: HomeService,
    private val homeNoticeRedDotService: HomeNoticeRedDotService
) : RemoteHomeDataSource {
    override suspend fun getHomeAllRoom(lastId: Int, size: Int): BaseResponse<HomeResponse> =
        homeService.getHomeAllRoom(lastId, size)

    override suspend fun readFinishHabitRoom(roomId: Int): NoDataResponse =
        homeHabitRoomFinishService.readFinishHabitRoom(roomId)

    override suspend fun getHomeNoticeRedDot(): BaseResponse<HomeNoticeRedDotResponese> =
        homeNoticeRedDotService.getHomeNoticeRedDot()

}
