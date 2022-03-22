package com.spark.android.ui.alarmcenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AlarmCenterViewModel : ViewModel() {
    private val _viewPagerPosition = MutableLiveData(VP_SPARK_ACTIVITY_POSITION)
    val viewPagerPosition: LiveData<Int> = _viewPagerPosition

    fun initVpPositionToSparkActivity() {
        _viewPagerPosition.value = VP_SPARK_ACTIVITY_POSITION
    }

    fun initVpPositionToNotice() {
        _viewPagerPosition.value = VP_NOTICE_POSITION
    }

    companion object {
        const val VP_SPARK_ACTIVITY_POSITION = 0
        const val VP_NOTICE_POSITION = 1
    }
}
