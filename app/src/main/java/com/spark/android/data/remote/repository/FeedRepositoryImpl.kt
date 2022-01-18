package com.spark.android.data.remote.repository

import com.spark.android.data.remote.datasource.FeedDataSource
import com.spark.android.data.remote.entity.response.BaseResponse
import com.spark.android.data.remote.entity.response.FeedResponse
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor(
    private val feedDataSource: FeedDataSource
) : FeedRepository {
    override suspend fun getFeedList(lastId: Int, size: Int): Result<BaseResponse<FeedResponse>> =
        kotlin.runCatching {
            feedDataSource.getFeedList(lastId,size)
        }
}
