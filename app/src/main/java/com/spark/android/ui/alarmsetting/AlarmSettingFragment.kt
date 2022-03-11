package com.spark.android.ui.alarmsetting

import android.os.Bundle
import android.view.View
import com.spark.android.R
import com.spark.android.databinding.FragmentAlarmSettingBinding
import com.spark.android.ui.base.BaseFragment
import com.spark.android.util.popBackStack

class AlarmSettingFragment :
    BaseFragment<FragmentAlarmSettingBinding>(R.layout.fragment_alarm_setting) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBackBtnClickListener()
    }

    private fun initBackBtnClickListener() {
        binding.btnAlarmSettingBack.setOnClickListener { popBackStack() }
    }
}
