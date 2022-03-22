package com.spark.android.ui.alarmcenter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AlarmCenterViewModel : ViewModel() {
    private val _vpPosition = MutableLiveData<Int>()
    val vpPosition: LiveData<Int> = _vpPosition

    private val _isVpPositionSparkActivity = MutableLiveData<Boolean>()
    val isVpPositionSparkActivity: LiveData<Boolean> = _isVpPositionSparkActivity

    private val _isVpPositionNotice = MutableLiveData<Boolean>()
    val isVpPositionNotice: LiveData<Boolean> = _isVpPositionNotice

    fun initVpPositionToSparkActivity() {
        _vpPosition.value = VP_SPARK_ACTIVITY_POSITION
        _isVpPositionSparkActivity.value = true
        _isVpPositionNotice.value = false
    }

    fun initVpPositionToNotice() {
        _vpPosition.value = VP_NOTICE_POSITION
        _isVpPositionNotice.value = true
        _isVpPositionSparkActivity.value = false
    }

    companion object {
        const val VP_SPARK_ACTIVITY_POSITION = 0
        const val VP_NOTICE_POSITION = 1
    }
}
