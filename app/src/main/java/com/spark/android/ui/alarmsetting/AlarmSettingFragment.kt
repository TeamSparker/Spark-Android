package com.spark.android.ui.alarmsetting

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.spark.android.R
import com.spark.android.databinding.FragmentAlarmSettingBinding
import com.spark.android.ui.base.BaseFragment
import com.spark.android.util.popBackStack
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlarmSettingFragment :
    BaseFragment<FragmentAlarmSettingBinding>(R.layout.fragment_alarm_setting) {
    private val alarmSettingViewModel by viewModels<AlarmSettingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBackBtnClickListener()
    }

    private fun initBackBtnClickListener() {
        binding.btnAlarmSettingBack.setOnClickListener { popBackStack() }
    }
}
