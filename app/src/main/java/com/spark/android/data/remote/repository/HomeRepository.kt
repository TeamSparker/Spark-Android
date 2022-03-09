package com.spark.android.data.remote.repository

import com.spark.android.data.remote.entity.response.HomeResponse
import com.spark.android.data.remote.entity.response.BaseResponse

interface HomeRepository {

    suspend fun getHomeAllRoom(lastId : Int, size : Int) : Result<BaseResponse<HomeResponse>>

    fun getHomeToastMessage():String

    fun getHomeToastMessageState():Boolean

}
