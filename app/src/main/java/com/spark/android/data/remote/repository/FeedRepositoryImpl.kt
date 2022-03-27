package com.spark.android.data.remote.repository

import com.spark.android.data.remote.datasource.FeedDataSource
import com.spark.android.data.remote.entity.response.BaseResponse
import com.spark.android.data.remote.entity.response.Feed
import com.spark.android.data.remote.entity.response.FeedListItem
import com.spark.android.data.remote.entity.response.FeedResponse
import com.spark.android.data.remote.entity.response.NoDataResponse
import com.spark.android.ui.feed.adapter.FeedAdapter
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor(
    private val feedDataSource: FeedDataSource
) : FeedRepository {
//    override fun getFeedList(size: Int): Flow<PagingData<FeedListItem>> =
//        feedDataSource.getFeedList(size)

    private var shownDate = ""

    override suspend fun getFeedList(lastId: Int, size: Int): Result<BaseResponse<FeedResponse>> =
        kotlin.runCatching {
            feedDataSource.getFeedList(lastId, size)
        }

    override suspend fun postFeedHeart(recordId: Int): Result<NoDataResponse> =
        kotlin.runCatching { feedDataSource.postFeedHeart(recordId) }

    override suspend fun postFeedReport(
        recordId: Int,
        reportReason: String
    ): Result<NoDataResponse> =
        kotlin.runCatching { feedDataSource.postFeedReport(recordId, reportReason) }

    override fun addHeaderToFeedList(feedList: List<Feed>): MutableList<FeedListItem> {
        val feedListWithHeader = mutableListOf<FeedListItem>()
        feedList.forEachIndexed { index, feed ->
            if (feed.date != shownDate) {
                shownDate = feed.date
                feedListWithHeader.add(
                    FeedListItem(
                        "$index$shownDate",
                        FeedAdapter.FEED_HEADER_TYPE,
                        formatDate(shownDate),
                        feed.day,
                        null
                    )
                )
            }
            feedListWithHeader.add(
                FeedListItem(
                    feed.recordId.toString(),
                    FeedAdapter.FEED_CONTENT_TYPE,
                    formatDate(shownDate),
                    feed.day,
                    feed
                )
            )
        }
        return feedListWithHeader
    }

    override fun formatDate(date: String): String {
        val dateArray = date.split("-")
        val year = dateArray[0]
        val month = dateArray[1]
        val dayOfMonth = dateArray[2]
        return "${year}년 ${month}월 ${dayOfMonth}일"
    }

    override fun initShownDate() {
        shownDate = ""
    }
}
