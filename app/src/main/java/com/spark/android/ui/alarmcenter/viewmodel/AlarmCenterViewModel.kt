package com.spark.android.ui.alarmcenter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AlarmCenterViewModel : ViewModel() {
    private val _vpPosition = MutableLiveData<Int>()
    val vpPosition: LiveData<Int> = _vpPosition

    private val _isVpPositionActivity = MutableLiveData<Boolean>()
    val isVpPositionActivity: LiveData<Boolean> = _isVpPositionActivity

    private val _isVpPositionNotice = MutableLiveData<Boolean>()
    val isVpPositionNotice: LiveData<Boolean> = _isVpPositionNotice

    fun initVpPositionToActivity() {
        _vpPosition.value = VP_ACTIVITY_ALARM
        _isVpPositionActivity.value = true
        _isVpPositionNotice.value = false
    }

    fun initVpPositionToNotice() {
        _vpPosition.value = VP_NOTICE_ALARM
        _isVpPositionNotice.value = true
        _isVpPositionActivity.value = false
    }

    companion object {
        const val VP_ACTIVITY_ALARM = 0
        const val VP_NOTICE_ALARM = 1
    }
}
