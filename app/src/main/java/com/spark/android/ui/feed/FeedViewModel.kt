package com.spark.android.ui.feed

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spark.android.data.remote.entity.response.Feed
import com.spark.android.data.remote.entity.response.FeedListItem
import com.spark.android.data.remote.repository.FeedRepository
import com.spark.android.ui.feed.adapter.FeedAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val feedRepository: FeedRepository
) : ViewModel() {
    private val _feedList = MutableLiveData<List<Feed>>()
    val feedList: LiveData<List<Feed>> = _feedList

    private val _feedListWithHeader = MutableLiveData<List<FeedListItem>>()
    val feedListWithHeader: LiveData<List<FeedListItem>> = _feedListWithHeader

    fun initFeedList() {
        viewModelScope.launch {
            feedRepository.getFeedList(-1, 10)
                .onSuccess { response ->
                    _feedList.postValue(response.data.feedList)
                }
                .onFailure {
                    Log.d("Feed_GetFeedList", it.message.toString())
                }
        }
    }

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
                        feed.day,
                        null
                    )
                )
            }
            feedListWithHeader.add(
                FeedListItem(
                    feed.recordId.toString(),
                    FeedAdapter.FEED_CONTENT_TYPE,
                    formatDate(date),
                    feed.day,
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
