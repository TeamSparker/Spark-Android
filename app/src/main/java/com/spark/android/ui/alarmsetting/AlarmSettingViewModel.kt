package com.spark.android.ui.alarmsetting

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spark.android.data.remote.repository.AlarmSettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmSettingViewModel @Inject constructor(
    private val alarmSettingRepository: AlarmSettingRepository
) : ViewModel() {
    val isOnCertification = MutableLiveData<Boolean>()
    val isOnConsider = MutableLiveData<Boolean>()
    val isOnRemind = MutableLiveData<Boolean>()
    val isOnRoomStart = MutableLiveData<Boolean>()
    val isOnSpark = MutableLiveData<Boolean>()

    fun getAlarmSetting() {
        viewModelScope.launch {
            alarmSettingRepository.getAlarmSetting()
                .onSuccess { response ->
                    isOnCertification.postValue(response.data.certification)
                    isOnConsider.postValue(response.data.consider)
                    isOnRemind.postValue(response.data.remind)
                    isOnRoomStart.postValue(response.data.roomStart)
                    isOnSpark.postValue(response.data.spark)
                }.onFailure {
                    Log.d("AlarmSetting_GetAlarmSetting", it.message.toString())
                }
        }
    }
}
