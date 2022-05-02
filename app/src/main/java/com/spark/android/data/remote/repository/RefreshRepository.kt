package com.spark.android.data.remote.repository

import com.spark.android.data.remote.entity.response.BaseResponse
import com.spark.android.data.remote.entity.response.RefreshResponse

interface RefreshRepository {

    suspend fun getRefresh(roomId:Int): Result<BaseResponse<RefreshResponse>>
}
