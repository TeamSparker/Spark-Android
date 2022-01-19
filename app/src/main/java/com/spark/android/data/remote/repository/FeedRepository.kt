package com.spark.android.data.remote.repository

import com.spark.android.data.remote.entity.response.BaseResponse
import com.spark.android.data.remote.entity.response.Feed
import com.spark.android.data.remote.entity.response.FeedListItem
import com.spark.android.data.remote.entity.response.FeedResponse

interface FeedRepository {
//    fun getFeedList(size: Int): Flow<PagingData<FeedListItem>>

    suspend fun getFeedList(lastId: Int, size: Int): Result<BaseResponse<FeedResponse>>
    fun addHeaderToFeedList(feedList: List<Feed>): List<FeedListItem>
    fun formatDate(date: String): String
}
