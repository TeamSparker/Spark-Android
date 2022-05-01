package com.spark.android.ui.feedreport

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.spark.android.R
import com.spark.android.databinding.ActivityFeedReportBinding
import com.spark.android.ui.base.BaseActivity
import com.spark.android.ui.feed.FeedBottomSheet.Companion.FEED_ITEM_ID
import com.spark.android.ui.main.MainActivity
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

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(MainActivity.FROM_WHERE, FROM_FEED_REPORT_ACTIVITY)
        })
        finish()
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
                startActivity(Intent(this, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_CLEAR_TASK
                    putExtra(MainActivity.FROM_WHERE, FROM_FEED_REPORT_ACTIVITY)
                })
                finish()
            }
        })
    }

    private fun initBackBtnClickListener() {
        binding.btnFeedReportBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_CLEAR_TASK
                putExtra(MainActivity.FROM_WHERE, FROM_FEED_REPORT_ACTIVITY)
            })
            finish()
        }
    }

    companion object {
        const val FROM_FEED_REPORT_ACTIVITY = "FeedReportActivity"
    }
}
