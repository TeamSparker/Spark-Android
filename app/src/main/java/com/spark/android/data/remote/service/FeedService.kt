package com.spark.android.data.remote.service

import com.spark.android.data.remote.entity.response.BaseResponse
import com.spark.android.data.remote.entity.response.FeedResponse
import com.spark.android.data.remote.entity.response.NoDataResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FeedService {
    @GET("feed")
    suspend fun getFeedList(
        @Query("lastid") lastId: Int,
        @Query("size") size: Int
    ): BaseResponse<FeedResponse>

    @POST("feed/{recordId}/like")
    suspend fun postFeedHeart(
        @Path("recordId") recordId: Int
    ): NoDataResponse
}
