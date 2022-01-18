package com.spark.android.ui.feed

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.spark.android.R
import com.spark.android.databinding.FragmentFeedBinding
import com.spark.android.ui.base.BaseFragment
import com.spark.android.ui.feed.adapter.FeedAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FeedFragment : BaseFragment<FragmentFeedBinding>(R.layout.fragment_feed) {
    private val feedViewModel by viewModels<FeedViewModel>()
    private val feedAdapter = FeedAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFeedRvAdapter()
        submitFeedData()
    }

    private fun initFeedRvAdapter() {
        binding.rvFeed.adapter = feedAdapter
    }

    private fun submitFeedData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                feedViewModel.getFeedPagingResource().collectLatest { feedList ->
                    feedAdapter.submitData(feedList)
//                    binding.rvFeed.addItemDecoration(
//                        FeedHeaderDecoration(
//                            FeedStickyHeaderResolverImpl(
//                                feedList,
//                                BR.feedDate,
//                                BR.feedDay
//                            ) { return@FeedStickyHeaderResolverImpl feedAdapter.isHeader(this) })
//                    )
                }
            }
        }
    }
}
