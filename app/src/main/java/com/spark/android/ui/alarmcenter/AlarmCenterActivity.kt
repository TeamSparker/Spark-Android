package com.spark.android.ui.alarmcenter

import android.os.Bundle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.spark.android.R
import com.spark.android.databinding.ActivityAlarmCenterBinding
import com.spark.android.ui.base.BaseActivity
import java.lang.IllegalArgumentException

class AlarmCenterActivity :
    BaseActivity<ActivityAlarmCenterBinding>(R.layout.activity_alarm_center) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewPagerAdapter()
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

    companion object {
        private const val VP_ITEM_COUNT = 2
    }
}
