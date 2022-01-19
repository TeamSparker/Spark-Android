package com.spark.android.ui.feed

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.spark.android.data.remote.entity.response.Feed
import com.spark.android.data.remote.entity.response.FeedListItem
import com.spark.android.data.remote.repository.FeedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
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
                    val feeds = response.data.feedList
                    lastId = feeds.last().recordId
                    _feedList.postValue(feedRepository.addHeaderToFeedList(feeds))
                }
                .onFailure {
                    Log.d("Feed_GetFeedList", it.message.toString())
                }
        }
    }

    companion object {
        private const val listLimit = 4
    }
}
