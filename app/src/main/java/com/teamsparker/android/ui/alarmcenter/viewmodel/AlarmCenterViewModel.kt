package com.teamsparker.android.ui.alarmcenter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teamsparker.android.ui.alarmcenter.alarms.AlarmType.Companion.ACTIVITY_ALARM
import com.teamsparker.android.ui.alarmcenter.alarms.AlarmType.Companion.SERVICE_ALARM
import com.teamsparker.android.util.Event

class AlarmCenterViewModel : ViewModel() {
    private val _vpPosition = MutableLiveData<Int>()
    val vpPosition: LiveData<Int> = _vpPosition

    private val _newServiceAlarm = MutableLiveData<Event<Boolean>>()
    val newServiceAlarm: LiveData<Event<Boolean>> = _newServiceAlarm

    private val _newActivityAlarm = MutableLiveData<Event<Boolean>>()
    val newActivityAlarm: LiveData<Event<Boolean>> = _newActivityAlarm

    fun initVpPositionToActivity() {
        _vpPosition.value = ACTIVITY_ALARM
    }

    fun initVpPositionToService() {
        _vpPosition.value = SERVICE_ALARM
    }

    fun initNewServiceAlarm(newService: Boolean) {
        _newServiceAlarm.value = Event(newService)
    }

    fun initNewActivityAlarm(newActivity: Boolean) {
        _newActivityAlarm.value = Event(newActivity)
    }
}
