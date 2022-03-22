package com.spark.android.ui.alarmcenter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AlarmCenterViewModel : ViewModel() {
    private val _vpPosition = MutableLiveData<Int>()
    val vpPosition: LiveData<Int> = _vpPosition

    private val _isVpPositionActivity = MutableLiveData<Boolean>()
    val isVpPositionActivity: LiveData<Boolean> = _isVpPositionActivity

    private val _isVpPositionService = MutableLiveData<Boolean>()
    val isVpPositionService: LiveData<Boolean> = _isVpPositionService

    fun initVpPositionToActivity() {
        _vpPosition.value = VP_ACTIVITY_ALARM
        _isVpPositionActivity.value = true
        _isVpPositionService.value = false
    }

    fun initVpPositionToService() {
        _vpPosition.value = VP_SERVICE_ALARM
        _isVpPositionService.value = true
        _isVpPositionActivity.value = false
    }

    companion object {
        const val VP_ACTIVITY_ALARM = 0
        const val VP_SERVICE_ALARM = 1
    }
}
