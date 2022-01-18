package com.spark.android.data.remote.repository

import androidx.paging.PagingData
import com.spark.android.data.remote.entity.response.FeedListItem
import kotlinx.coroutines.flow.Flow

interface FeedRepository {
    fun getFeedList(size: Int): Flow<PagingData<FeedListItem>>
}
