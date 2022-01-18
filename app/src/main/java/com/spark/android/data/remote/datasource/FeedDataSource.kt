package com.spark.android.data.remote.datasource

import androidx.paging.PagingData
import com.spark.android.data.remote.entity.response.FeedListItem
import kotlinx.coroutines.flow.Flow

interface FeedDataSource {
    fun getFeedList(size: Int): Flow<PagingData<FeedListItem>>
}
