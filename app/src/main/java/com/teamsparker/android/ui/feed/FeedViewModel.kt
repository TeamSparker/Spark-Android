package com.teamsparker.android.ui.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamsparker.android.data.remote.entity.response.FeedListItem
import com.teamsparker.android.data.remote.repository.FeedRepository
import com.teamsparker.android.ui.feed.adapter.FeedAdapter.Companion.FEED_FOOTER_TYPE
import com.teamsparker.android.ui.feed.adapter.FeedAdapter.Companion.FEED_LOADING_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val feedRepository: FeedRepository
) : ViewModel() {
//    fun getFeedPagingResource(): Flow<PagingData<FeedListItem>> =
//        feedRepository.getFeedList(size = 4).cachedIn(viewModelScope)

    var lastId = -1
        private set
    var hasNextPage = true
        private set

    var isAddLoading = false
        private set

    var isRefresh = false
        private set

    private val _isFeedEmpty = MutableLiveData(false)
    val isFeedEmpty: LiveData<Boolean> = _isFeedEmpty

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val loadingItem =
        FeedListItem(id = "loading", viewType = FEED_LOADING_TYPE, feed = null)

    private val _feedList = MutableLiveData(mutableListOf<FeedListItem>())
    val feedList: LiveData<MutableList<FeedListItem>> = _feedList

    fun initShownDate() {
        feedRepository.initShownDate()
    }

    private fun initIsFeedEmpty() {
        _isFeedEmpty.value = true
    }

    private fun initIsLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun postFeedHeart(feedListItem: FeedListItem) {
        viewModelScope.launch {
            val feed = requireNotNull(feedListItem.feed)
            feedRepository.postFeedHeart(feed.recordId)
                .onFailure { Timber.tag("Feed_PostFeedHeart").d(it.message.toString()) }
            when (feed.isLiked) {
                true -> feed.likeNum--
                else -> feed.likeNum++
            }
            feed.isLiked = !feed.isLiked
        }
    }

    fun getFeedList() {
        if (requireNotNull(feedList.value).isEmpty()) {
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
                    } else if (lastId == -1) {
                        initIsFeedEmpty()
                    }
                    val feeds = feedRepository.addHeaderToFeedList(tempFeeds, isRefresh)
                    isRefresh = false
                    if (tempFeeds.size < listLimit && lastId != -1) {
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
                }.onFailure {
                    Timber.tag("Feed_GetFeedList").d(it.message.toString())
                }
        }
    }

    private fun addLoadingItem() {
        _feedList.value = requireNotNull(_feedList.value).toMutableList().apply { add(loadingItem) }
    }

    fun canGetNewFeeds() = !isAddLoading && !isRefresh

    fun refreshFeedList() {
        lastId = -1
        hasNextPage = true
        isRefresh = true
        _feedList.value = mutableListOf()
        getFeedList()
    }

    companion object {
        private const val listLimit = 5
    }
}
