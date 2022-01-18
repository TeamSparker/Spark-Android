package com.spark.android.data.remote.repository

import com.spark.android.data.remote.entity.response.BaseResponse
import com.spark.android.data.remote.entity.response.RefreshResponse
import com.spark.android.data.remote.service.RefreshService
import javax.inject.Inject

class RefreshRepositoryImpl @Inject constructor(
    private val refreshService: RefreshService
) : RefreshRepository{
    override suspend fun getRefresh(
        roomId: Int
    ) = refreshService.getRefresh(roomId)
}