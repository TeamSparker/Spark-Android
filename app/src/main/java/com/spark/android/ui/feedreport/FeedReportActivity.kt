package com.spark.android.ui.feedreport

import android.os.Bundle
import androidx.activity.viewModels
import com.spark.android.R
import com.spark.android.databinding.ActivityFeedReportBinding
import com.spark.android.ui.base.BaseActivity
import com.spark.android.ui.feed.FeedBottomSheet.Companion.FEED_ITEM_ID
import com.spark.android.util.EventObserver
import com.spark.android.util.KeyBoardUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedReportActivity : BaseActivity<ActivityFeedReportBinding>(R.layout.activity_feed_report) {
    private val feedReportViewModel by viewModels<FeedReportViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.feedReportViewModel = feedReportViewModel
        initFeedItemId()
        initLayoutClickListener()
        initIsFocused()
        initIsSuccessReportObserver()
        initBackBtnClickListener()
    }

    private fun initFeedItemId() {
        feedReportViewModel.initFeedItemId(intent.getIntExtra(FEED_ITEM_ID, -1))
    }

    private fun initLayoutClickListener() {
        binding.layoutFeedReport.setOnClickListener {
            KeyBoardUtil.hide(this)
        }
    }

    private fun initIsFocused() {
        binding.etFeedReport.setOnFocusChangeListener { _, hasFocus ->
            feedReportViewModel.initCauseFocused(hasFocus)
        }
    }

    private fun initIsSuccessReportObserver() {
        feedReportViewModel.isSuccessReport.observe(this, EventObserver { isSuccess ->
            if (isSuccess) {
                finish()
            }
        })
    }

    private fun initBackBtnClickListener() {
        binding.btnFeedReportBack.setOnClickListener {
            finish()
        }
    }
}
