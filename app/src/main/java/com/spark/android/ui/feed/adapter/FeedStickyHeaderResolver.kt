package com.spark.android.ui.feed.adapter

import androidx.databinding.ViewDataBinding
import com.spark.android.data.remote.entity.response.FeedListItem

interface StickyHeaderResolver {
    fun isHeader(position: Int): Boolean
    fun getItemHeaderForPos(idx: Int): FeedListItem?
    fun ViewDataBinding.bindHeader(item: FeedListItem)
}
