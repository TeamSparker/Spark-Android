package com.spark.android.ui.alarmcenter

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.spark.android.R
import com.spark.android.databinding.ActivityAlarmCenterBinding
import com.spark.android.ui.alarmcenter.viewmodel.AlarmCenterViewModel
import com.spark.android.ui.alarmcenter.viewmodel.AlarmCenterViewModel.Companion.VP_NOTICE_ALARM
import com.spark.android.ui.alarmcenter.viewmodel.AlarmCenterViewModel.Companion.VP_ACTIVITY_ALARM
import com.spark.android.ui.base.BaseActivity
import com.spark.android.util.initStatusBarColor
import com.spark.android.util.initStatusBarTextColorToWhite
import java.lang.IllegalArgumentException

class AlarmCenterActivity :
    BaseActivity<ActivityAlarmCenterBinding>(R.layout.activity_alarm_center) {
    private val alarmCenterViewModel by viewModels<AlarmCenterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.alarmCenterViewModel = alarmCenterViewModel
        initStatusBarStyle()
        initBackBtnClickListener()
        initVpAlarmCenterAdapter()
        initVpAlarmCenterListener()
        initVpPositionObserver()
    }

    private fun initStatusBarStyle() {
        initStatusBarColor(R.color.spark_white)
        initStatusBarTextColorToWhite()
    }

    private fun initBackBtnClickListener() {
        binding.btnAlarmCenterBack.setOnClickListener { finish() }
    }

    private fun initVpAlarmCenterAdapter() {
        binding.vpAlarmCenter.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = VP_ITEM_COUNT

            override fun createFragment(position: Int) = when (position) {
                VP_ACTIVITY_ALARM -> ActivityAlarmFragment()
                VP_NOTICE_ALARM -> NoticeAlarmFragment()
                else -> throw IllegalArgumentException("알림 센터 뷰페이저 position 범위 벗어난 오류")
            }
        }
    }

    private fun initVpAlarmCenterListener() {
        binding.vpAlarmCenter.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    VP_ACTIVITY_ALARM -> alarmCenterViewModel.initVpPositionToActivity()
                    VP_NOTICE_ALARM -> alarmCenterViewModel.initVpPositionToNotice()
                }
            }
        })
    }

    private fun initVpPositionObserver() {
        alarmCenterViewModel.vpPosition.observe(this) { position ->
            when (position) {
                VP_ACTIVITY_ALARM -> {
                    binding.vpAlarmCenter.currentItem = VP_ACTIVITY_ALARM
                    startIndicatorAnimator(binding.viewAlarmCenterActivity)
                }
                VP_NOTICE_ALARM -> {
                    binding.vpAlarmCenter.currentItem = VP_NOTICE_ALARM
                    startIndicatorAnimator(binding.viewAlarmCenterNotice)
                }
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
