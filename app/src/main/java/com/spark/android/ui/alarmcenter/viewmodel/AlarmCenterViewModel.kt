package com.spark.android.ui.alarmcenter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spark.android.ui.alarmcenter.alarms.AlarmType.Companion.ACTIVITY_ALARM
import com.spark.android.ui.alarmcenter.alarms.AlarmType.Companion.SERVICE_ALARM

class AlarmCenterViewModel : ViewModel() {
    private val _vpPosition = MutableLiveData<Int>()
    val vpPosition: LiveData<Int> = _vpPosition
    fun initVpPositionToActivity() {
        _vpPosition.value = ACTIVITY_ALARM
    }

    fun initVpPositionToService() {
        _vpPosition.value = SERVICE_ALARM
    }
}
