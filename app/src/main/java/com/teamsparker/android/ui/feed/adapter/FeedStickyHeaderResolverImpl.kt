package com.teamsparker.android.ui.feed.adapter

import androidx.databinding.ViewDataBinding
import com.teamsparker.android.data.remote.entity.response.FeedListItem

class FeedStickyHeaderResolverImpl(
    private val items: List<FeedListItem>,
    private val dateVarBind: Int,
    private val dayVarBind: Int,
    private val isHeader: FeedListItem.() -> Boolean
) : StickyHeaderResolver {

    override fun getItemHeaderForPos(idx: Int): FeedListItem? {
        var header: FeedListItem? = null
        for ((i, item) in items.withIndex()) {
            if (isHeader(item)) {
                header = item
            }
            if (i == idx) {
                break
            }
        }
        return header
    }


    override fun ViewDataBinding.bindHeader(item: FeedListItem) {
        setVariable(dateVarBind, item.headerDate)
        setVariable(dayVarBind, item.headerDay)
        executePendingBindings()
    }

    override fun isHeader(position: Int) = isHeader(items[position])
}
