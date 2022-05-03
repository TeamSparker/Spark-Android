package com.teamsparker.android.ui.alarmcenter.alarms.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.teamsparker.android.data.remote.entity.response.Alarm
import com.teamsparker.android.data.remote.repository.AlarmCenterRepository
import com.teamsparker.android.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ServiceAlarmViewModel @Inject constructor(
    private val alarmCenterRepository: AlarmCenterRepository
) : ViewModel() {
    private val _emptyServiceAlarm = MutableLiveData<Event<Boolean>>()
    val emptyServiceAlarm: LiveData<Event<Boolean>> = _emptyServiceAlarm

    private val _newActivity = MutableLiveData<Event<Boolean>>()
    val newActivity: LiveData<Event<Boolean>> = _newActivity

    fun initEmptyServiceAlarm(isEmpty: Boolean) {
        _emptyServiceAlarm.postValue(Event(isEmpty))
    }

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
                    Timber.tag("AlarmCenter_PatchServiceAlarm").d(it.message.toString())
                }
        }
    }
}
