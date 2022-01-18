package com.spark.android.data.remote.datasource

import com.spark.android.data.remote.entity.response.BaseResponse
import com.spark.android.data.remote.entity.response.FeedResponse

interface FeedDataSource {
    suspend fun getFeedList(lastId: Int, size: Int): BaseResponse<FeedResponse>
}
