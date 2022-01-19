package com.spark.android.ui.feed

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spark.android.data.remote.entity.response.FeedListItem
import com.spark.android.data.remote.repository.FeedRepository
import com.spark.android.ui.feed.adapter.FeedAdapter.Companion.FEED_FOOTER_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val feedRepository: FeedRepository
) : ViewModel() {
//    fun getFeedPagingResource(): Flow<PagingData<FeedListItem>> =
//        feedRepository.getFeedList(size = 4).cachedIn(viewModelScope)

    private var lastId = -1

    private val _feedList = MutableLiveData<List<FeedListItem>>()
    val feedList: LiveData<List<FeedListItem>> = _feedList

    fun getFeedList() {
        viewModelScope.launch {
            feedRepository.getFeedList(lastId, listLimit)
                .onSuccess { response ->
                    val tempFeeds = response.data.feedList
                    lastId = tempFeeds.last().recordId
                    val feeds = feedRepository.addHeaderToFeedList(tempFeeds)
                    if (tempFeeds.size < listLimit) {
                        feeds.add(
                            FeedListItem(id = "footer", viewType = FEED_FOOTER_TYPE, feed = null)
                        )
                    }
                    _feedList.postValue(feeds)
                }
                .onFailure {
                    Log.d("Feed_GetFeedList", it.message.toString())
                }
        }
    }

    companion object {
        private const val listLimit = 5
    }
}
