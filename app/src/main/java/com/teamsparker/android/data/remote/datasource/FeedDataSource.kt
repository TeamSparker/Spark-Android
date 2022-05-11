package com.teamsparker.android.data.remote.datasource

import com.teamsparker.android.data.remote.entity.request.FeedReportRequest
import com.teamsparker.android.data.remote.entity.response.BaseResponse
import com.teamsparker.android.data.remote.entity.response.FeedResponse
import com.teamsparker.android.data.remote.entity.response.NoDataResponse

interface FeedDataSource {
//    fun getFeedList(size: Int): Flow<PagingData<FeedListItem>>

    suspend fun getFeedList(lastId: Int, size: Int): BaseResponse<FeedResponse>

    suspend fun postFeedHeart(recordId: Int): NoDataResponse

    suspend fun postFeedReport(recordId: Int, body: FeedReportRequest): NoDataResponse
}
