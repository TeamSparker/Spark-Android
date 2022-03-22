package com.spark.android.ui.alarmcenter

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.spark.android.R
import com.spark.android.databinding.ActivityAlarmCenterBinding
import com.spark.android.ui.base.BaseActivity
import java.lang.IllegalArgumentException

class AlarmCenterActivity :
    BaseActivity<ActivityAlarmCenterBinding>(R.layout.activity_alarm_center) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBackBtnClickListener()
        initSparkActivityAlarmTvClickListener()
        initNoticeAlarmTvClickListener()
        initViewPagerAdapter()
    }

    private fun initBackBtnClickListener() {
        binding.btnAlarmCenterBack.setOnClickListener { finish() }
    }

    private fun initSparkActivityAlarmTvClickListener() {
        binding.tvAlarmCenterTabActivity.setOnClickListener {
            startIndicatorAnimator(binding.viewAlarmCenterActivity)
        }
    }

    private fun initNoticeAlarmTvClickListener() {
        binding.tvAlarmCenterTabNotice.setOnClickListener {
            startIndicatorAnimator(binding.viewAlarmCenterNotice)
        }
    }

    private fun initViewPagerAdapter() {
        binding.vpAlarmCenter.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = VP_ITEM_COUNT

            override fun createFragment(position: Int) = when (position) {
                0 -> SparkActivityAlarmFragment()
                1 -> NoticeAlarmFragment()
                else -> throw IllegalArgumentException("알림 센터 뷰페이저 position 범위 벗어난 오류")
            }
        }
    }

    private fun startIndicatorAnimator(view: View) {
        val indicatorAnim = ObjectAnimator.ofFloat(view, "scaleX", 0f, 1.0f)
        view.pivotX = 0f
        indicatorAnim.duration = 150
        indicatorAnim.start()
    }

    companion object {
        private const val VP_ITEM_COUNT = 2
    }
}
