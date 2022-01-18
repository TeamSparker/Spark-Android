package com.spark.android.data.remote.datasource

import com.spark.android.data.remote.entity.response.BaseResponse
import com.spark.android.data.remote.entity.response.FeedResponse
import com.spark.android.data.remote.service.FeedService
import javax.inject.Inject

class FeedDataSourceImpl @Inject constructor(
    private val feedService: FeedService
) : FeedDataSource {
    override suspend fun getFeedList(lastId: Int, size: Int): BaseResponse<FeedResponse> =
        feedService.getFeedList(lastId, size)
}
