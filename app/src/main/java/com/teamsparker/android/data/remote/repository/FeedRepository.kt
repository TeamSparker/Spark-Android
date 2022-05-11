package com.teamsparker.android.data.remote.repository

import com.teamsparker.android.data.remote.entity.request.FeedReportRequest
import com.teamsparker.android.data.remote.entity.response.BaseResponse
import com.teamsparker.android.data.remote.entity.response.Feed
import com.teamsparker.android.data.remote.entity.response.FeedListItem
import com.teamsparker.android.data.remote.entity.response.FeedResponse
import com.teamsparker.android.data.remote.entity.response.NoDataResponse

interface FeedRepository {
//    fun getFeedList(size: Int): Flow<PagingData<FeedListItem>>

    suspend fun getFeedList(lastId: Int, size: Int): Result<BaseResponse<FeedResponse>>

    suspend fun postFeedHeart(recordId: Int): Result<NoDataResponse>

    suspend fun postFeedReport(recordId: Int, body: FeedReportRequest): Result<NoDataResponse>

    fun addHeaderToFeedList(feedList: List<Feed>, isRefresh: Boolean): MutableList<FeedListItem>

    fun formatDate(date: String): String

    fun initShownDate()
}
