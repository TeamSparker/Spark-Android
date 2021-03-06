package com.teamsparker.android.data.remote.datasource

import com.teamsparker.android.data.remote.entity.request.FeedReportRequest
import com.teamsparker.android.data.remote.entity.response.BaseResponse
import com.teamsparker.android.data.remote.entity.response.FeedResponse
import com.teamsparker.android.data.remote.entity.response.NoDataResponse
import com.teamsparker.android.data.remote.service.FeedService
import javax.inject.Inject

class FeedDataSourceImpl @Inject constructor(
    private val feedService: FeedService
) : FeedDataSource {
//    override fun getFeedList(size: Int): Flow<PagingData<FeedListItem>> =
//        Pager(
//            config = PagingConfig(pageSize = size, enablePlaceholders = false),
//            pagingSourceFactory = { FeedPagingSource(feedService, size) }
//        ).flow

    override suspend fun getFeedList(lastId: Int, size: Int): BaseResponse<FeedResponse> =
        feedService.getFeedList(lastId, size)

    override suspend fun postFeedHeart(recordId: Int): NoDataResponse =
        feedService.postFeedHeart(recordId)

    override suspend fun postFeedReport(recordId: Int, body: FeedReportRequest): NoDataResponse =
        feedService.postFeedReport(recordId, body)
}
