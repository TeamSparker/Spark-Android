package com.spark.android.ui.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spark.android.data.remote.entity.response.Feed
import com.spark.android.data.remote.entity.response.FeedListItem
import com.spark.android.ui.feed.adapter.FeedAdapter

class FeedViewModel : ViewModel() {
    private val _feedList = MutableLiveData(listOf<Feed>())
    val feedList: LiveData<List<Feed>> = _feedList

    private val _feedListWithHeader = MutableLiveData(listOf<FeedListItem>())
    val feedListWithHeader: LiveData<List<FeedListItem>> = _feedListWithHeader

    fun initFeedList() {}

    fun addHeaderToFeedList() {
        var date = ""
        val feedListWithHeader = mutableListOf<FeedListItem>()
        requireNotNull(feedList.value).forEachIndexed { index, feed ->
            if (feed.date != date) {
                date = feed.date
                feedListWithHeader.add(
                    FeedListItem(
                        "$index$date",
                        FeedAdapter.FEED_HEADER_TYPE,
                        formatDate(date),
                        "${feed.day}요일",
                        null
                    )
                )
            }
            feedListWithHeader.add(
                FeedListItem(
                    feed.recordId.toString(),
                    FeedAdapter.FEED_CONTENT_TYPE,
                    formatDate(date),
                    "${feed.day}요일",
                    feed
                )
            )
        }
        initFeedListWithHeader(feedListWithHeader)
    }

    private fun initFeedListWithHeader(list: List<FeedListItem>) {
        _feedListWithHeader.value = list
    }

    private fun formatDate(date: String): String {
        val dateArray = date.split("-")
        val year = dateArray[0]
        val month = dateArray[1]
        val dayOfMonth = dateArray[2]
        return "${year}년 ${month}월 ${dayOfMonth}일"
    }
}
