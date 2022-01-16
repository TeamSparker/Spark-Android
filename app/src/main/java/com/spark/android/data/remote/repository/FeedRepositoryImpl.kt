package com.spark.android.data.remote.repository

import com.spark.android.data.remote.datasource.FeedDataSource
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor(
    private val feedDataSource: FeedDataSource
) : FeedRepository {
}
