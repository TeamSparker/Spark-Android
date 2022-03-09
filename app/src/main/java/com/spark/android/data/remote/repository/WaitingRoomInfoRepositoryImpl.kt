package com.spark.android.data.remote.repository

import com.spark.android.data.local.datasource.LocalPreferencesWaitingRoomDataSource
import com.spark.android.data.remote.datasource.RemoteWaitingRoomDataSource
import com.spark.android.data.remote.entity.response.BaseResponse
import com.spark.android.data.remote.entity.response.NoDataResponse
import com.spark.android.data.remote.entity.response.WaitingRoomInfoResponse
import com.spark.android.data.remote.service.WaitingRoomInfoService
import javax.inject.Inject

class WaitingRoomInfoRepositoryImpl @Inject constructor(
    private val localPreferencesWaitingRoomDataSource: LocalPreferencesWaitingRoomDataSource,
    private val remoteWaitingRoomDataSource: RemoteWaitingRoomDataSource
) : WaitingRoomInfoRepository {

    override suspend fun getWaitingRoomInfo(roomId: Int) =
        kotlin.runCatching { remoteWaitingRoomDataSource.getWaitingRoomInfo(roomId) }

    override suspend fun deleteWaitingRoom(roomId: Int) =
        kotlin.runCatching { remoteWaitingRoomDataSource.deleteWaitingRoom(roomId) }

    override suspend fun leaveRoom(roomId: Int) =
        kotlin.runCatching { remoteWaitingRoomDataSource.leaveRoom(roomId) }


    override fun setHomeToastMessage(message: String) {
        localPreferencesWaitingRoomDataSource.setHomeToastMessage(message)
    }

    override fun setHomeToastMessageState(state: Boolean) {
        localPreferencesWaitingRoomDataSource.setHomeToastMessageState(state)
    }
}