package com.spark.android.ui.feed

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
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
        feedViewModel.initFeedList()
        initFeedRvAdapter()
        initFeedListObserver()
        initFeedListWithHeaderObserver()
    }

    private fun initFeedRvAdapter() {
        binding.rvFeed.adapter = feedAdapter
    }

    private fun initFeedListObserver() {
        feedViewModel.feedList.observe(viewLifecycleOwner) {
            feedViewModel.addHeaderToFeedList()
        }
    }

    private fun initFeedListWithHeaderObserver() {
        feedViewModel.feedListWithHeader.observe(viewLifecycleOwner) { list ->
            feedAdapter.submitList(list)
            binding.rvFeed.addItemDecoration(
                FeedHeaderDecoration(
                    FeedStickyHeaderResolverImpl(
                        list,
                        BR.feedDate,
                        BR.feedDay
                    ) { return@FeedStickyHeaderResolverImpl feedAdapter.isHeader(this) })
            )
        }
    }
}
