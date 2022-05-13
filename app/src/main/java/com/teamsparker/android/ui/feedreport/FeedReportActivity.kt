package com.teamsparker.android.ui.feedreport

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.teamsparker.android.R
import com.teamsparker.android.databinding.ActivityFeedReportBinding
import com.teamsparker.android.ui.base.BaseActivity
import com.teamsparker.android.ui.feed.FeedBottomSheet.Companion.FEED_ITEM_ID
import com.teamsparker.android.ui.main.MainActivity
import com.teamsparker.android.util.EventObserver
import com.teamsparker.android.util.KeyBoardUtil
import com.teamsparker.android.util.initStatusBarColor
import com.teamsparker.android.util.initStatusBarTextColorToWhite
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedReportActivity : BaseActivity<ActivityFeedReportBinding>(R.layout.activity_feed_report) {
    private val feedReportViewModel by viewModels<FeedReportViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.feedReportViewModel = feedReportViewModel
        initStatusBarStyle()
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

    private fun initStatusBarStyle() {
        initStatusBarColor(R.color.spark_white)
        initStatusBarTextColorToWhite()
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
                    putExtra(FEED_REPORT_SUCCESS, true)
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
        const val FEED_REPORT_SUCCESS = "feedReportSuccess"
    }
}
