package com.spark.android.ui.feed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.spark.android.BR
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.spark.android.data.remote.response.FeedListItem
import com.spark.android.databinding.ItemFeedContentBinding
import com.spark.android.databinding.ItemFeedHeaderBinding
import java.lang.IllegalStateException

class FeedAdapter : ListAdapter<FeedListItem, RecyclerView.ViewHolder>(feedDiffUtil) {
    class FeedHeaderViewHolder(private val binding: ItemFeedHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(feedListItem: FeedListItem) {
            binding.setVariable(BR.feedDate, feedListItem.headerDate)
            binding.setVariable(BR.feedDay, feedListItem.headerDay)
            binding.executePendingBindings()
        }
    }

    class FeedContentViewHolder(private val binding: ItemFeedContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(feedListItem: FeedListItem) {
            feedListItem.feed?.let { feed ->
                binding.setVariable(BR.feed, feed)
                binding.executePendingBindings()
            }
        }
    }

    override fun getItemViewType(position: Int) = getItem(position).viewType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            FEED_HEADER_TYPE -> FeedHeaderViewHolder(
                ItemFeedHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            FEED_CONTENT_TYPE -> FeedContentViewHolder(
                ItemFeedContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            else -> throw IllegalStateException("피드 뷰타입 설정 오류")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FeedHeaderViewHolder -> holder.bind(getItem(position))
            is FeedContentViewHolder -> holder.bind(getItem(position))
        }
    }

    fun isHeader(item: FeedListItem) = item.viewType == FEED_HEADER_TYPE

    companion object {
        const val FEED_HEADER_TYPE = 0
        const val FEED_CONTENT_TYPE = 1

        private val feedDiffUtil = object : DiffUtil.ItemCallback<FeedListItem>() {
            override fun areItemsTheSame(oldItem: FeedListItem, newItem: FeedListItem): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: FeedListItem, newItem: FeedListItem): Boolean =
                oldItem == newItem

        }
    }
}
