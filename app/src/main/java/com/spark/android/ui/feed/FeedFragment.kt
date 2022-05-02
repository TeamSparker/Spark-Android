package com.spark.android.ui.feed

import android.os.Bundle
import android.view.View
import androidx.core.animation.doOnEnd
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.spark.android.BR
import com.spark.android.R
import com.spark.android.databinding.FragmentFeedBinding
import com.spark.android.ui.base.BaseFragment
import com.spark.android.ui.feed.FeedBottomSheet.Companion.FEED
import com.spark.android.ui.feed.FeedBottomSheet.Companion.FEED_ITEM_ID
import com.spark.android.ui.feed.adapter.FeedAdapter
import com.spark.android.ui.feed.adapter.FeedHeaderDecoration
import com.spark.android.ui.feed.adapter.FeedStickyHeaderResolverImpl
import com.spark.android.util.AnimationUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedFragment : BaseFragment<FragmentFeedBinding>(R.layout.fragment_feed) {
    private val feedViewModel by viewModels<FeedViewModel>()
    private val args by navArgs<FeedFragmentArgs>()
    private val feedAdapter =
        FeedAdapter(
            { recordId -> feedViewModel.postFeedHeart(recordId) },
            { itemId -> showBottomSheet(itemId) }
        )
    private val feedReportToastAnimator by lazy {
        requireNotNull(AnimationUtil.grayBoxToastAnimation(binding.tvFeedToastFeedReport)).apply {
            doOnEnd {
                binding.tvFeedToastFeedReport.visibility = View.GONE
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.feedViewModel = feedViewModel
        showFeedReportSuccessToast()
        feedViewModel.initShownDate()
        feedViewModel.getFeedList()
        initSwipeRefreshLayout()
        initFeedRvAdapter()
        addScrollListenerToFeedRv()
        initFeedListObserver()
    }

    override fun onPause() {
        super.onPause()
        if (feedReportToastAnimator.isRunning) {
            feedReportToastAnimator.cancel()
        }
    }

    private fun initSwipeRefreshLayout() {
        with(binding.swipeRefreshFeed) {
            setColorSchemeColors(requireContext().getColor(R.color.spark_pinkred))
            setOnRefreshListener {
                feedViewModel.refreshFeedList()
                isRefreshing = false
            }
        }
    }

    private fun initFeedRvAdapter() {
        binding.rvFeed.adapter = feedAdapter
    }

    private fun initFeedListObserver() {
        feedViewModel.feedList.observe(viewLifecycleOwner) { list ->
            if (binding.rvFeed.itemDecorationCount == 1) {
                binding.rvFeed.removeItemDecorationAt(0)
            }
            binding.rvFeed.addItemDecoration(
                FeedHeaderDecoration(
                    FeedStickyHeaderResolverImpl(
                        list,
                        BR.feedDate,
                        BR.feedDay
                    ) { return@FeedStickyHeaderResolverImpl feedAdapter.isHeader(this) })
            )
            feedAdapter.submitList(list)
        }
    }

    private fun addScrollListenerToFeedRv() {
        binding.rvFeed.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = binding.rvFeed.layoutManager as LinearLayoutManager
                val lastPosition = layoutManager.findLastCompletelyVisibleItemPosition()
                if (feedViewModel.hasNextPage) {
                    if (feedViewModel.canGetNewFeeds() && layoutManager.itemCount <= lastPosition + LOAD_POSITION &&
                        !binding.rvFeed.canScrollVertically(STATE_LOWEST)
                    ) {
                        feedViewModel.getFeedList()
                    }
                }
            }
        })
    }

    private fun showBottomSheet(itemId: Int) {
        setFragmentResult(FEED, bundleOf(FEED_ITEM_ID to itemId))
        FeedBottomSheet().show(parentFragmentManager, this.javaClass.name)
    }

    private fun showFeedReportSuccessToast() {
        if (args.feedReportSuccess) {
            binding.tvFeedToastFeedReport.visibility = View.VISIBLE
            feedReportToastAnimator.start()
        }
    }

    companion object {
        private const val LOAD_POSITION = 2
        private const val STATE_LOWEST = 1
    }
}
