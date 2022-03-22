package com.spark.android.ui.alarmcenter.alarms.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.spark.android.data.remote.entity.response.Alarm
import com.spark.android.data.remote.repository.AlarmCenterRepository
import com.spark.android.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityAlarmViewModel @Inject constructor(
    private val alarmCenterRepository: AlarmCenterRepository
) : ViewModel() {
    private val _newService = MutableLiveData<Event<Boolean>>()
    val newService: LiveData<Event<Boolean>> = _newService

    private fun initNewService(newService: Boolean) {
        _newService.value = Event(newService)
    }

    fun getActivityAlarmPagingSource(): Flow<PagingData<Alarm>> =
        alarmCenterRepository.getActivityAlarmList(size = 10) { newService ->
            initNewService(newService)
        }.cachedIn(viewModelScope)

    fun patchActivityAlarm() {
        viewModelScope.launch {
            alarmCenterRepository.patchActivityAlarm()
                .onFailure {
                    Log.d("AlarmCenter_PatchActivityAlarm", it.message.toString())
                }
        }
    }
}
