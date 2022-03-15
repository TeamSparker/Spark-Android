package com.spark.android.ui.alarmsetting

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.spark.android.R
import com.spark.android.databinding.FragmentAlarmSettingBinding
import com.spark.android.ui.alarmsetting.AlarmSettingViewModel.Companion.ALARM_CERTIFICATION
import com.spark.android.ui.alarmsetting.AlarmSettingViewModel.Companion.ALARM_CONSIDER
import com.spark.android.ui.alarmsetting.AlarmSettingViewModel.Companion.ALARM_REMIND
import com.spark.android.ui.alarmsetting.AlarmSettingViewModel.Companion.ALARM_ROOM_START
import com.spark.android.ui.alarmsetting.AlarmSettingViewModel.Companion.ALARM_SPARK
import com.spark.android.ui.base.BaseFragment
import com.spark.android.util.popBackStack
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlarmSettingFragment :
    BaseFragment<FragmentAlarmSettingBinding>(R.layout.fragment_alarm_setting) {
    private val alarmSettingViewModel by viewModels<AlarmSettingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.alarmSettingViewModel = alarmSettingViewModel
        alarmSettingViewModel.getAlarmSetting()
        initBackBtnClickListener()
        initCertificationSwitchClickListener()
        initConsiderSwitchClickListener()
        initReminderSwitchClickListener()
        initRoomStartSwitchClickListener()
        initSparkSwitchClickListener()
    }

    private fun initBackBtnClickListener() {
        binding.btnAlarmSettingBack.setOnClickListener { popBackStack() }
    }

    private fun initCertificationSwitchClickListener() {
        binding.switchAlarmSettingCertification.setOnClickListener {
            alarmSettingViewModel.patchAlarmSetting(ALARM_CERTIFICATION)
        }
    }

    private fun initConsiderSwitchClickListener() {
        binding.switchAlarmSettingConsider.setOnClickListener {
            alarmSettingViewModel.patchAlarmSetting(ALARM_CONSIDER)
        }
    }

    private fun initReminderSwitchClickListener() {
        binding.switchAlarmSettingNotDone.setOnClickListener {
            alarmSettingViewModel.patchAlarmSetting(ALARM_REMIND)
        }
    }

    private fun initRoomStartSwitchClickListener() {
        binding.switchAlarmSettingRoomStart.setOnClickListener {
            alarmSettingViewModel.patchAlarmSetting(ALARM_ROOM_START)
        }
    }

    private fun initSparkSwitchClickListener() {
        binding.switchAlarmSettingSendSpark.setOnClickListener {
            alarmSettingViewModel.patchAlarmSetting(ALARM_SPARK)
        }
    }
}
