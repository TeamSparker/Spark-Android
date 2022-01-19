package com.spark.android.data.remote.repository

import com.spark.android.data.remote.entity.response.BaseResponse
import com.spark.android.data.remote.entity.response.Feed
import com.spark.android.data.remote.entity.response.FeedListItem
import com.spark.android.data.remote.entity.response.FeedResponse
import com.spark.android.data.remote.entity.response.NoDataResponse

interface FeedRepository {
//    fun getFeedList(size: Int): Flow<PagingData<FeedListItem>>

    suspend fun getFeedList(lastId: Int, size: Int): Result<BaseResponse<FeedResponse>>
    suspend fun postFeedHeart(recordId: Int): Result<NoDataResponse>
    fun addHeaderToFeedList(feedList: List<Feed>): MutableList<FeedListItem>
    fun formatDate(date: String): String
}
