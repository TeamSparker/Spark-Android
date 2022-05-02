package com.spark.android.ui.alarmsetting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spark.android.data.remote.entity.response.AlarmSettingResponse
import com.spark.android.data.remote.repository.AlarmSettingRepository
import com.spark.android.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AlarmSettingViewModel @Inject constructor(
    private val alarmSettingRepository: AlarmSettingRepository
) : ViewModel() {
    private val _isAlarmSettingLocalSaved =
        MutableLiveData(Event(alarmSettingRepository.getAlarmSettingLocalSaved()))
    val isAlarmSettingLocalSaved: LiveData<Event<Boolean>> = _isAlarmSettingLocalSaved

    private val _alarmSettingValue = MutableLiveData(alarmSettingRepository.getAlarmSettingValue())
    val alarmSettingValue: LiveData<AlarmSettingResponse> = _alarmSettingValue

    fun getAlarmSetting() {
        if (!alarmSettingRepository.getAlarmSettingLocalSaved()) {
            viewModelScope.launch {
                alarmSettingRepository.getAlarmSetting()
                    .onSuccess { response ->
                        alarmSettingRepository.saveAlarmSettingLocalSaved(true)
                        _isAlarmSettingLocalSaved.postValue(Event(true))
                        alarmSettingRepository.saveAlarmSettingValue(response.data)
                        _alarmSettingValue.postValue(response.data)
                    }.onFailure {
                        Timber.tag("AlarmSetting_GetAlarmSetting").d(it.message.toString())
                    }
            }
        }
    }

    fun patchAlarmSetting(category: String) {
        viewModelScope.launch {
            alarmSettingRepository.patchAlarmSetting(category)
                .onSuccess {
                    alarmSettingRepository.patchAlarmSettingValue(category)
                    _alarmSettingValue.postValue(alarmSettingRepository.getAlarmSettingValue())
                }
                .onFailure {
                    Timber.tag("AlarmSetting_PatchAlarmSetting").d(it.message.toString())
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
