package com.spark.android.ui.feed

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.spark.android.data.remote.entity.response.Feed
import com.spark.android.data.remote.service.FeedService
import java.lang.Exception
import java.lang.NullPointerException

class FeedPagingSource(
    private val service: FeedService,
    private val limit: Int
) : PagingSource<Int, Feed>() {
    private var currentIdKey: Int = 1
    private val lastIdMap = hashMapOf<Int, Int>()

    init {
        initFirstId()
    }

    override fun getRefreshKey(state: PagingState<Int, Feed>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            when {
                anchorPage?.prevKey != null -> {
                    lastIdMap[currentIdKey + 1]
                }
                anchorPage?.nextKey != null -> {
                    lastIdMap[currentIdKey - 1]
                }
                else -> {
                    null
                }
            }
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Feed> {
        return try {
            currentIdKey = params.key ?: 1
            val lastId = lastIdMap[currentIdKey] ?: throw NullPointerException("lastIdMap에 없는 키 접근")
            lastIdMap[currentIdKey] = lastId
            val response = service.getFeedList(lastId, limit).data.feedList
            return LoadResult.Page(
                data = response,
                prevKey = if (currentIdKey == 1) null else lastIdMap[currentIdKey - 1],
                nextKey = if (response.isEmpty()) null else response.last().recordId
            )
        } catch (e: Exception) {
            Log.d(this.javaClass.toString(), e.message.toString())
            LoadResult.Error(e)
        }
    }


    private fun initFirstId() {
        lastIdMap[1] = FIRST_ID
    }

    companion object {
        private const val FIRST_ID = -1
    }
}
