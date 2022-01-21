package com.spark.android.data.remote.repository

import com.spark.android.data.remote.entity.response.HomeResponse
import com.spark.android.data.remote.entity.response.BaseResponse

interface HomeRepository {

    suspend fun getHomeAllRoom(lastid : Int, size : Int) : Result<BaseResponse<HomeResponse>>
}