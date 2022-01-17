package com.spark.android.data.remote.service

import com.spark.android.data.remote.entity.response.HomeResponse
import com.spark.android.data.remote.entity.response.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeService {

    @GET("room")
    suspend fun getHomeAllRoom(
        @Query("lastid") lastid: Int,
        @Query("size") size: Int
    ): BaseResponse<HomeResponse>


}