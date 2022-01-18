package com.spark.android.data.remote.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.spark.android.data.remote.entity.response.FeedListItem
import com.spark.android.data.remote.service.FeedService
import com.spark.android.ui.feed.FeedPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FeedDataSourceImpl @Inject constructor(
    private val feedService: FeedService
) : FeedDataSource {
    override fun getFeedList(size: Int): Flow<PagingData<FeedListItem>> =
        Pager(
            config = PagingConfig(pageSize = size, enablePlaceholders = false),
            pagingSourceFactory = { FeedPagingSource(feedService, size) }
        ).flow
}
