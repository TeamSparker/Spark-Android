package com.teamsparker.android.ui.feed.adapter

import android.animation.Animator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teamsparker.android.BR
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teamsparker.android.R
import com.teamsparker.android.data.remote.entity.response.FeedListItem
import com.teamsparker.android.databinding.ItemFeedContentBinding
import com.teamsparker.android.databinding.ItemFeedFooterBinding
import com.teamsparker.android.databinding.ItemFeedHeaderBinding
import com.teamsparker.android.databinding.ItemFeedLoadingBinding
import com.teamsparker.android.util.FirebaseLogUtil
import com.teamsparker.android.util.FirebaseLogUtil.CLICK_HEART_FEED
import java.lang.IllegalStateException

class FeedAdapter(
    private val postHeart: (FeedListItem) -> Unit,
    private val showMore: (Int) -> Unit
) : ListAdapter<FeedListItem, RecyclerView.ViewHolder>(feedDiffUtil) {
    class FeedHeaderViewHolder(private val binding: ItemFeedHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(feedListItem: FeedListItem) {
            binding.setVariable(BR.feedDate, feedListItem.headerDate)
            binding.setVariable(BR.feedDay, feedListItem.headerDay)
            binding.executePendingBindings()
        }
    }

    class FeedContentViewHolder(
        private val binding: ItemFeedContentBinding,
        private val postHeart: (FeedListItem) -> Unit,
        private val showMore: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            initMoreBtnClickListener()
        }

        fun bind(feedListItem: FeedListItem) {
            feedListItem.feed?.let { feed ->
                binding.setVariable(BR.feed, feed)
                binding.executePendingBindings()
                initHeartClickListener(feedListItem)
            }
        }

        private fun initMoreBtnClickListener() {
            binding.btnFeedMore.setOnClickListener {
                showMore(requireNotNull(binding.feed).recordId)
            }
        }

        private fun initHeartClickListener(feedListItem: FeedListItem) {
            binding.btnFeedHeart.setOnClickListener {
                binding.btnFeedHeart.isEnabled = false
                val feed = requireNotNull(feedListItem.feed)
                binding.lottieFeedHeart.let { lottie ->
                    when (feed.isLiked) {
                        true -> {
                            binding.btnFeedHeart.isEnabled = true
                            binding.btnFeedHeart.setImageResource(R.drawable.ic_feed_heart_gray)
                            binding.tvFeedHeartCount.let {
                                it.setTextColor(it.context.getColor(R.color.spark_gray))
                                it.text = (feed.likeNum - 1).toString()
                            }
                        }
                        false -> {
                            FirebaseLogUtil.logClickEvent(CLICK_HEART_FEED)
                            binding.btnFeedHeart.setImageResource(R.drawable.ic_feed_heart_pink)
                            binding.tvFeedHeartCount.let {
                                it.setTextColor(it.context.getColor(R.color.spark_pinkred))
                                it.text = (feed.likeNum + 1).toString()
                            }
                            lottie.let {
                                it.visibility = View.VISIBLE
                                it.playAnimation()
                                it.addAnimatorListener(object : Animator.AnimatorListener {
                                    override fun onAnimationEnd(animation: Animator?) {
                                        it.visibility = View.INVISIBLE
                                        binding.btnFeedHeart.isEnabled = true
                                    }

                                    override fun onAnimationStart(animation: Animator?) {}
                                    override fun onAnimationCancel(animation: Animator?) {}
                                    override fun onAnimationRepeat(animation: Animator?) {}

                                })
                            }
                        }
                    }
                }
                postHeart(feedListItem)
            }
        }
    }

    class FeedFooterViewHolder(binding: ItemFeedFooterBinding) :
        RecyclerView.ViewHolder(binding.root)

    class FeedLoadingViewHolder(binding: ItemFeedLoadingBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun getItemViewType(position: Int) = getItem(position).viewType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            FEED_HEADER_TYPE -> FeedHeaderViewHolder(
                ItemFeedHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            FEED_CONTENT_TYPE -> FeedContentViewHolder(
                ItemFeedContentBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                postHeart,
                showMore
            )
            FEED_FOOTER_TYPE -> FeedFooterViewHolder(
                ItemFeedFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            FEED_LOADING_TYPE -> FeedLoadingViewHolder(
                ItemFeedLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            else -> throw IllegalStateException("?????? ????????? ?????? ??????")
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
        const val FEED_FOOTER_TYPE = 2
        const val FEED_LOADING_TYPE = 3

        private val feedDiffUtil = object : DiffUtil.ItemCallback<FeedListItem>() {
            override fun areItemsTheSame(oldItem: FeedListItem, newItem: FeedListItem): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: FeedListItem, newItem: FeedListItem): Boolean =
                oldItem == newItem

        }
    }
}
