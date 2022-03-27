package com.spark.android.ui.feedreport

import android.os.Bundle
import androidx.activity.viewModels
import com.spark.android.R
import com.spark.android.databinding.ActivityFeedReportBinding
import com.spark.android.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedReportActivity : BaseActivity<ActivityFeedReportBinding>(R.layout.activity_feed_report) {
    private val feedReportViewModel by viewModels<FeedReportViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.feedReportViewModel = feedReportViewModel
        initIsFocused()
    }

    private fun initIsFocused() {
        binding.etFeedReport.setOnFocusChangeListener { _, hasFocus ->
            feedReportViewModel.initCauseFocused(hasFocus)
        }
    }
}
