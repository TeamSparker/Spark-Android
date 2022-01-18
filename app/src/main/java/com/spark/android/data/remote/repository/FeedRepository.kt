package com.spark.android.data.remote.repository

import com.spark.android.data.remote.entity.response.BaseResponse
import com.spark.android.data.remote.entity.response.FeedResponse

interface FeedRepository {
    suspend fun getFeedList(lastId: Int, size: Int): Result<BaseResponse<FeedResponse>>
}
