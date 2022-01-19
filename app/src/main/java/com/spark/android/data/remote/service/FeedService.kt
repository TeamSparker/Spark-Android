package com.spark.android.data.remote.service

import com.spark.android.data.remote.entity.response.BaseResponse
import com.spark.android.data.remote.entity.response.FeedResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FeedService {
    @GET("feed")
    suspend fun getFeedList(
        @Query("lastid") lastId: Int,
        @Query("size") size: Int
    ): BaseResponse<FeedResponse>
}
