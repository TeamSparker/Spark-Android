package com.spark.android.data.remote.repository

import com.spark.android.data.local.datasource.LocalPreferencesHomeDataSource
import com.spark.android.data.remote.datasource.RemoteHomeDataSource
import com.spark.android.data.remote.entity.response.BaseResponse
import com.spark.android.data.remote.entity.response.HomeNoticeRedDotResponese
import com.spark.android.data.remote.entity.response.NoDataResponse
import com.spark.android.data.remote.service.HomeService
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val remoteHomeDataSource: RemoteHomeDataSource,
    private val localPreferencesHomeDataSource: LocalPreferencesHomeDataSource
) : HomeRepository {
    override suspend fun getHomeAllRoom(lastId: Int, size: Int) = kotlin.runCatching {
        remoteHomeDataSource.getHomeAllRoom(lastId, size)
    }

    override suspend fun readFinishHabitRoom(roomId: Int) = kotlin.runCatching {
        remoteHomeDataSource.readFinishHabitRoom(roomId)
    }

    override suspend fun getHomeNoticeRedDot() = kotlin.runCatching {
        remoteHomeDataSource.getHomeNoticeRedDot()
    }

    override fun getHomeToastMessage(): String =
        localPreferencesHomeDataSource.getHomeToastMessage()

    override fun getHomeToastMessageState(): Boolean =
        localPreferencesHomeDataSource.getHomeToastMessageState()


    override fun setHomeToastMessage(message: String) {
        localPreferencesHomeDataSource.setHomeToastMessage(message)
    }

    override fun setHomeToastMessageState(state: Boolean) {
        localPreferencesHomeDataSource.setHomeToastMessageState(state)
    }
}
