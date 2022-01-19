package com.spark.android.ui.feed

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spark.android.data.remote.entity.response.FeedListItem
import com.spark.android.data.remote.repository.FeedRepository
import com.spark.android.ui.feed.adapter.FeedAdapter.Companion.FEED_FOOTER_TYPE
import com.spark.android.ui.feed.adapter.FeedAdapter.Companion.FEED_LOADING_TYPE
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
    var hasNextPage = true
        private set

    var isAddLoading = false
        private set

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val loadingItem =
        FeedListItem(id = "loading", viewType = FEED_LOADING_TYPE, feed = null)

    private val _feedList = MutableLiveData(mutableListOf<FeedListItem>())
    val feedList: LiveData<MutableList<FeedListItem>> = _feedList

    private fun initIsLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun getFeedList() {
        if(requireNotNull(feedList.value).isEmpty()){
            initIsLoading(true)
        }
        if (requireNotNull(feedList.value).isNotEmpty() && hasNextPage) {
            isAddLoading = true
            addLoadingItem()
        }
        viewModelScope.launch {
            feedRepository.getFeedList(lastId, listLimit)
                .onSuccess { response ->
                    val tempFeeds = response.data.feedList
                    if (tempFeeds.isNotEmpty()) {
                        lastId = tempFeeds.last().recordId
                    }
                    val feeds = feedRepository.addHeaderToFeedList(tempFeeds)
                    if (tempFeeds.size < listLimit) {
                        hasNextPage = false
                        feeds.add(
                            FeedListItem(id = "footer", viewType = FEED_FOOTER_TYPE, feed = null)
                        )
                    }
                    isAddLoading = false
                    initIsLoading(false)
                    _feedList.postValue(
                        requireNotNull(_feedList.value).toMutableList().apply {
                            remove(loadingItem)
                            addAll(feeds)
                        })
                }
                .onFailure {
                    Log.d("Feed_GetFeedList", it.message.toString())
                }
        }
    }

    private fun addLoadingItem() {
        _feedList.value = requireNotNull(_feedList.value).toMutableList().apply { add(loadingItem) }
    }

    companion object {
        private const val listLimit = 5
    }
}
