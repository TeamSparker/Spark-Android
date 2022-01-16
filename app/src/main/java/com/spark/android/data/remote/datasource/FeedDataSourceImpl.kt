package com.spark.android.data.remote.datasource

import com.spark.android.data.remote.service.FeedService
import javax.inject.Inject

class FeedDataSourceImpl @Inject constructor(
    private val feedService: FeedService
) : FeedDataSource {
}
