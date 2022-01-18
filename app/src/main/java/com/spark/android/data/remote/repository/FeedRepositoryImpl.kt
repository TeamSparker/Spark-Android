package com.spark.android.data.remote.repository

import androidx.paging.PagingData
import com.spark.android.data.remote.datasource.FeedDataSource
import com.spark.android.data.remote.entity.response.FeedListItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor(
    private val feedDataSource: FeedDataSource
) : FeedRepository {
    override fun getFeedList(size: Int): Flow<PagingData<FeedListItem>> =
        feedDataSource.getFeedList(size)
}
