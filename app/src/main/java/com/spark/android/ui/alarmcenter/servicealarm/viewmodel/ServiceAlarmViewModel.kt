package com.spark.android.ui.alarmcenter.servicealarm.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.spark.android.data.remote.entity.response.Alarm
import com.spark.android.data.remote.repository.AlarmCenterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServiceAlarmViewModel @Inject constructor(
    private val alarmCenterRepository: AlarmCenterRepository
): ViewModel() {
    fun getServiceAlarmPagingSource(): Flow<PagingData<Alarm>> =
        alarmCenterRepository.getServiceAlarmList(size = 10).cachedIn(viewModelScope)

    fun patchServiceAlarm() {
        viewModelScope.launch {
            alarmCenterRepository.patchServiceAlarm()
                .onFailure {
                    Log.d("AlarmCenter_PatchServiceAlarm", it.message.toString())
                }
        }
    }
}
