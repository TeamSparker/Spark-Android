package com.spark.android.ui.alarmcenter.acitivityalarm

import android.os.Bundle
import android.view.View
import com.spark.android.R
import com.spark.android.databinding.FragmentActivityAlarmBinding
import com.spark.android.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityAlarmFragment :
    BaseFragment<FragmentActivityAlarmBinding>(R.layout.fragment_activity_alarm) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
