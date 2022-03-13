package com.spark.android.ui.alarmsetting

import androidx.lifecycle.ViewModel
import com.spark.android.data.remote.repository.AlarmSettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AlarmSettingViewModel @Inject constructor(
    private val alarmSettingRepository: AlarmSettingRepository
) : ViewModel() {
}
