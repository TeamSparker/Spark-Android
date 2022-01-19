package com.spark.android.ui.feed

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.spark.android.BR
import com.spark.android.R
import com.spark.android.databinding.FragmentFeedBinding
import com.spark.android.ui.base.BaseFragment
import com.spark.android.ui.feed.adapter.FeedAdapter
import com.spark.android.ui.feed.adapter.FeedHeaderDecoration
import com.spark.android.ui.feed.adapter.FeedStickyHeaderResolverImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedFragment : BaseFragment<FragmentFeedBinding>(R.layout.fragment_feed) {
    private val feedViewModel by viewModels<FeedViewModel>()
    private val feedAdapter = FeedAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        feedViewModel.getFeedList()
        initFeedRvAdapter()
        addScrollListenerToFeedRv()
        initFeedListObserver()
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
                    if (!feedViewModel.isAddLoading && layoutManager.itemCount <= lastPosition + LOAD_POSITION &&
                        !binding.rvFeed.canScrollVertically(STATE_LOWEST)
                    ) {
                        feedViewModel.getFeedList()
                    }
                }
            }
        })
    }

    companion object {
        private const val LOAD_POSITION = 2
        private const val STATE_LOWEST = 1
    }
}
