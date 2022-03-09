package com.spark.android.data.remote.repository

import com.spark.android.data.local.datasource.LocalPreferencesHomeDataSource
import com.spark.android.data.remote.service.HomeService
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeService: HomeService,
    private val localPreferencesHomeDataSource: LocalPreferencesHomeDataSource
) : HomeRepository {
    override suspend fun getHomeAllRoom(lastId: Int, size: Int) = kotlin.runCatching {
        homeService.getHomeAllRoom(lastId, size)
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
