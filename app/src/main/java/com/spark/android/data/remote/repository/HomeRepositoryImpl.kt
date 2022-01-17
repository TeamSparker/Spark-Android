package com.spark.android.data.remote.repository

import com.spark.android.data.remote.service.HomeService
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeService: HomeService
) : HomeRepository {

    override suspend fun getHomeAllRoom(
        lastid: Int,
        size: Int
    ) = homeService.getHomeAllRoom(lastid, size)

}