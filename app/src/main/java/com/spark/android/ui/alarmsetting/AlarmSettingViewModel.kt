package com.spark.android.ui.alarmsetting

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spark.android.data.remote.repository.AlarmSettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class AlarmSettingViewModel @Inject constructor(
    private val alarmSettingRepository: AlarmSettingRepository
) : ViewModel() {
    private val _isOnCertification = MutableLiveData<Boolean>()
    val isOnCertification: LiveData<Boolean> = _isOnCertification

    private val _isOnConsider = MutableLiveData<Boolean>()
    val isOnConsider: LiveData<Boolean> = _isOnConsider

    private val _isOnRemind = MutableLiveData<Boolean>()
    val isOnRemind: LiveData<Boolean> = _isOnRemind

    private val _isOnRoomStart = MutableLiveData<Boolean>()
    val isOnRoomStart: LiveData<Boolean> = _isOnRoomStart

    private val _isOnSpark = MutableLiveData<Boolean>()
    val isOnSpark: LiveData<Boolean> = _isOnSpark

    fun getAlarmSetting() {
        viewModelScope.launch {
            alarmSettingRepository.getAlarmSetting()
                .onSuccess { response ->
                    _isOnCertification.postValue(response.data.certification)
                    _isOnConsider.postValue(response.data.consider)
                    _isOnRemind.postValue(response.data.remind)
                    _isOnRoomStart.postValue(response.data.roomStart)
                    _isOnSpark.postValue(response.data.spark)
                }.onFailure {
                    Log.d("AlarmSetting_GetAlarmSetting", it.message.toString())
                }
        }
    }

    fun patchAlarmSetting(category: String) {
        viewModelScope.launch {
            alarmSettingRepository.patchAlarmSetting(category)
                .onFailure {
                    Log.d("AlarmSetting_PatchAlarmSetting", it.message.toString())
                }
        }
    }

    companion object {
        const val ALARM_CERTIFICATION = "certification"
        const val ALARM_CONSIDER = "consider"
        const val ALARM_REMIND = "remind"
        const val ALARM_ROOM_START = "roomStart"
        const val ALARM_SPARK = "spark"
    }
}
