package com.spark.android.data.remote.datasource

import com.spark.android.data.remote.entity.response.BaseResponse
import com.spark.android.data.remote.entity.response.HomeNoticeRedDotResponese
import com.spark.android.data.remote.entity.response.HomeResponse
import com.spark.android.data.remote.entity.response.NoDataResponse

interface RemoteHomeDataSource {

    suspend fun getHomeAllRoom(lastId: Int, size: Int): BaseResponse<HomeResponse>

    suspend fun readFinishHabitRoom(roomId: Int): NoDataResponse

    suspend fun getHomeNoticeRedDot(): BaseResponse<HomeNoticeRedDotResponese>
}
