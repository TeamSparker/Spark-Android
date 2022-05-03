package com.teamsparker.android.data.remote.service

import com.teamsparker.android.data.remote.entity.request.FeedReportRequest
import com.teamsparker.android.data.remote.entity.response.BaseResponse
import com.teamsparker.android.data.remote.entity.response.FeedResponse
import com.teamsparker.android.data.remote.entity.response.NoDataResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FeedService {
    @GET("feed")
    suspend fun getFeedList(
        @Query("lastId") lastId: Int,
        @Query("size") size: Int
    ): BaseResponse<FeedResponse>

    @POST("feed/{recordId}/like")
    suspend fun postFeedHeart(
        @Path("recordId") recordId: Int
    ): NoDataResponse

    @POST("feed/{recordId}/report")
    suspend fun postFeedReport(
        @Path("recordId") recordId: Int,
        @Body body: FeedReportRequest
    ): NoDataResponse
}
