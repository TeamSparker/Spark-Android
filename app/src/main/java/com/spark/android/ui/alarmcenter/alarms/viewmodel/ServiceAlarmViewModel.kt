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
class ServiceAlarmViewModel @Inject constructor(
    private val alarmCenterRepository: AlarmCenterRepository
) : ViewModel() {
    private val _newActivity = MutableLiveData<Event<Boolean>>()
    val newActivity: LiveData<Event<Boolean>> = _newActivity

    private fun initNewActivity(newActivity: Boolean) {
        _newActivity.value = Event(newActivity)
    }

    fun getServiceAlarmPagingSource(): Flow<PagingData<Alarm>> =
        alarmCenterRepository.getServiceAlarmList(size = 10) { newActivity ->
            initNewActivity(newActivity)
        }.cachedIn(viewModelScope)

    fun patchServiceAlarm() {
        viewModelScope.launch {
            alarmCenterRepository.patchServiceAlarm()
                .onFailure {
                    Log.d("AlarmCenter_PatchServiceAlarm", it.message.toString())
                }
        }
    }
}
