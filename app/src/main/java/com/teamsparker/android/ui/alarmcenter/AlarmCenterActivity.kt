package com.teamsparker.android.ui.alarmcenter

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.teamsparker.android.R
import com.teamsparker.android.databinding.ActivityAlarmCenterBinding
import com.teamsparker.android.ui.alarmcenter.alarms.ActivityAlarmFragment
import com.teamsparker.android.ui.alarmcenter.alarms.AlarmType.Companion.ACTIVITY_ALARM
import com.teamsparker.android.ui.alarmcenter.alarms.AlarmType.Companion.SERVICE_ALARM
import com.teamsparker.android.ui.alarmcenter.alarms.ServiceAlarmFragment
import com.teamsparker.android.ui.alarmcenter.viewmodel.AlarmCenterViewModel
import com.teamsparker.android.ui.base.BaseActivity
import com.teamsparker.android.util.initStatusBarColor
import com.teamsparker.android.util.initStatusBarTextColorToWhite
import dagger.hilt.android.AndroidEntryPoint
import java.lang.IllegalArgumentException

@AndroidEntryPoint
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
        binding.vpAlarmCenter.apply {
            isUserInputEnabled = false
            adapter = object : FragmentStateAdapter(this@AlarmCenterActivity) {
                override fun getItemCount() = VP_ITEM_COUNT

                override fun createFragment(position: Int) = when (position) {
                    ACTIVITY_ALARM -> ActivityAlarmFragment()
                    SERVICE_ALARM -> ServiceAlarmFragment()
                    else -> throw IllegalArgumentException("알림 센터 뷰페이저 position 범위 벗어난 오류")
                }
            }
            (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }
    }

    private fun initVpAlarmCenterListener() {
        binding.vpAlarmCenter.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    ACTIVITY_ALARM -> alarmCenterViewModel.initVpPositionToActivity()
                    SERVICE_ALARM -> alarmCenterViewModel.initVpPositionToService()
                }
            }
        })
    }

    private fun initVpPositionObserver() {
        alarmCenterViewModel.vpPosition.observe(this) { position ->
            when (position) {
                ACTIVITY_ALARM -> {
                    binding.vpAlarmCenter.currentItem = ACTIVITY_ALARM
                    startIndicatorAnimator(binding.viewAlarmCenterActivity)
                    initNewActivityAlarmSticker(false)
                }
                SERVICE_ALARM -> {
                    binding.vpAlarmCenter.currentItem = SERVICE_ALARM
                    startIndicatorAnimator(binding.viewAlarmCenterService)
                    initNewServiceAlarmSticker(false)
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

    fun initNewServiceAlarmSticker(newService: Boolean) {
        alarmCenterViewModel.initNewServiceAlarm(newService)
    }

    fun initNewActivityAlarmSticker(newActivity: Boolean) {
        alarmCenterViewModel.initNewActivityAlarm(newActivity)
    }

    companion object {
        private const val VP_ITEM_COUNT = 2
    }
}
