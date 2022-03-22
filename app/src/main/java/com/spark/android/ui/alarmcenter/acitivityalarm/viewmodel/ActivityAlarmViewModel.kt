package com.spark.android.ui.alarmcenter.acitivityalarm.viewmodel

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
class ActivityAlarmViewModel @Inject constructor(
    private val alarmCenterRepository: AlarmCenterRepository
) : ViewModel() {
    fun getActivityAlarmPagingSource(): Flow<PagingData<Alarm>> =
        alarmCenterRepository.getActivityAlarmList(size = 10).cachedIn(viewModelScope)

    fun patchActivityAlarm() {
        viewModelScope.launch {
            alarmCenterRepository.patchActivityAlarm()
                .onFailure {
                    Log.d("AlarmCenter_PatchActivityAlarm", it.message.toString())
                }
        }
    }
}
