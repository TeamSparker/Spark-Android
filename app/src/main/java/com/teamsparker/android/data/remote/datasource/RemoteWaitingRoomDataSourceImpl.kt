package com.teamsparker.android.data.remote.datasource

import com.teamsparker.android.data.remote.entity.response.BaseResponse
import com.teamsparker.android.data.remote.entity.response.NoDataResponse
import com.teamsparker.android.data.remote.entity.response.WaitingRoomInfoResponse
import com.teamsparker.android.data.remote.service.DeleteRoomService
import com.teamsparker.android.data.remote.service.LeaveRoomService
import com.teamsparker.android.data.remote.service.WaitingRoomInfoService
import javax.inject.Inject

class RemoteWaitingRoomDataSourceImpl @Inject constructor(
    private val waitingRoomInfoService: WaitingRoomInfoService,
    private val deleteRoomService: DeleteRoomService,
    private val leaveRoomService: LeaveRoomService
) : RemoteWaitingRoomDataSource {
    override suspend fun getWaitingRoomInfo(roomId: Int): BaseResponse<WaitingRoomInfoResponse> =
        waitingRoomInfoService.getWaitingRoomInfo(roomId)

    override suspend fun deleteWaitingRoom(roomId: Int): NoDataResponse =
        deleteRoomService.deleteWaitingRoom(roomId)

    override suspend fun leaveRoom(roomId: Int): NoDataResponse =
        leaveRoomService.leaveRoom(roomId)

}
