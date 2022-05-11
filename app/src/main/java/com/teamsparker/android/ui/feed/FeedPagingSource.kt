package com.teamsparker.android.ui.feed

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.teamsparker.android.data.remote.entity.response.Feed
import com.teamsparker.android.data.remote.entity.response.FeedListItem
import com.teamsparker.android.data.remote.service.FeedService
import com.teamsparker.android.ui.feed.adapter.FeedAdapter
import java.lang.Exception

class FeedPagingSource(
    private val service: FeedService,
    private val limit: Int
) : PagingSource<Int, FeedListItem>() {
    private var currentIdKey: Int = 1
    private val lastIdMap = hashMapOf<Int, Int>()
    private var shownDate = ""

    init {
        initFirstId()
    }

    override fun getRefreshKey(state: PagingState<Int, FeedListItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            when {
                anchorPage?.prevKey != null -> {
                    lastIdMap[++currentIdKey]
                }
                anchorPage?.nextKey != null -> {
                    lastIdMap[--currentIdKey]
                }
                else -> {
                    null
                }
            }
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FeedListItem> {
        return try {
            val idKey = currentIdKey
            val lastId = params.key ?: -1
            val feedList = service.getFeedList(lastId, limit).data.feedList
            if (feedList.size == limit) {
                lastIdMap[idKey + 1] = feedList.last().recordId
            }
            val response = addHeaderToFeedList(feedList)
            return LoadResult.Page(
                data = response,
                prevKey = if (idKey <= 1) null else lastIdMap[idKey - 1],
                nextKey = if (feedList.size < limit) null else lastIdMap[idKey + 1]
            )
        } catch (e: Exception) {
            Log.d(this.javaClass.toString(), e.message.toString())
            LoadResult.Error(e)
        }
    }

    private fun initFirstId() {
        lastIdMap[1] = FIRST_ID
    }

    private fun addHeaderToFeedList(feedList: List<Feed>): List<FeedListItem> {
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

    private fun formatDate(date: String): String {
        val dateArray = date.split("-")
        val year = dateArray[0]
        val month = dateArray[1]
        val dayOfMonth = dateArray[2]
        return "${year}년 ${month}월 ${dayOfMonth}일"
    }

    companion object {
        private const val FIRST_ID = -1
    }
}
