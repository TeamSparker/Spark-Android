package com.teamsparker.android.ui.waitingroom.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamsparker.android.data.remote.entity.response.Member
import com.teamsparker.android.data.remote.entity.response.WaitingRoomInfoResponse
import com.teamsparker.android.data.remote.repository.RefreshRepository
import com.teamsparker.android.data.remote.repository.StartHabitRepository
import com.teamsparker.android.data.remote.repository.WaitingRoomInfoRepository
import com.teamsparker.android.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WaitingRoomViewModel @Inject constructor(
    private val waitingRoomInfoRepository: WaitingRoomInfoRepository,
    private val refreshRepository: RefreshRepository,
    private val startHabitRepository: StartHabitRepository
) : ViewModel() {
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _waitingRoomInfo = MutableLiveData<WaitingRoomInfoResponse>()
    val waitingRoomInfo: LiveData<WaitingRoomInfoResponse> = _waitingRoomInfo

    private val _refreshInfo = MutableLiveData<List<Member>>()
    val refreshInfo: LiveData<List<Member>> = _refreshInfo

    private val _memberListSize = MutableLiveData<Int>()
    val memberListSize: LiveData<Int> = _memberListSize

    private val _deleteWaitingRoomState = MutableLiveData<Event<Boolean>>()
    val deleteWaitingRoomState: LiveData<Event<Boolean>> = _deleteWaitingRoomState

    private val _leaveWaitingRoomState = MutableLiveData<Event<Boolean>>()
    val leaveWaitingRoomState: LiveData<Event<Boolean>> = _leaveWaitingRoomState

    private val _startHabitRoomState = MutableLiveData<Event<Boolean>>()
    val startHabitRoomState: LiveData<Event<Boolean>> = _startHabitRoomState

    fun initIsLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun getWaitingRoomInfo(roomId: Int) {
        initIsLoading(true)
        viewModelScope.launch {
            waitingRoomInfoRepository.getWaitingRoomInfo(roomId)
                .onSuccess {
                    _waitingRoomInfo.postValue(it.data!!)
                    initIsLoading(false)
                }.onFailure {
                    Timber.tag("WaitingRoomInfo").d(it.message.toString())
                }
        }
    }

    fun initMemberListSize() {
        _memberListSize.postValue(waitingRoomInfo.value?.members?.size ?: 0)
    }

    fun getRefreshInfo(roomId: Int) {
        viewModelScope.launch {
            refreshRepository.getRefresh(roomId)
                .onSuccess {
                    _refreshInfo.postValue(it.data.members)
                }.onFailure {
                    Timber.tag("refreshPeopleList").d(it.message.toString())
                }
        }
    }

    fun updateMemberListSize() {
        _memberListSize.postValue(_refreshInfo.value?.size ?: 0)
    }

    fun startHabit(roomId: Int) {
        viewModelScope.launch {
            startHabitRepository.startHabit(roomId)
                .onSuccess {
                    _startHabitRoomState.postValue(Event(true))
                }
                .onFailure {
                    Timber.tag("startHabit").d(it.message.toString())
                }
        }
    }

    fun deleteWaitingRoom(roomId: Int) {
        viewModelScope.launch {
            waitingRoomInfoRepository.deleteWaitingRoom(roomId)
                .onSuccess {
                    _deleteWaitingRoomState.postValue(Event(true))
                }
                .onFailure {
                    Timber.tag("deleteWaitingRoom").d(it.message.toString())
                }
        }
    }

    fun leaveRoom(roomId: Int) {
        viewModelScope.launch {
            waitingRoomInfoRepository.leaveRoom(roomId)
                .onSuccess {
                    _leaveWaitingRoomState.postValue(Event(true))
                }
                .onFailure {
                    Timber.tag("leaveRoom").d(it.message.toString())
                }
        }
    }


    fun setHomeToastMessage(message: String) {
        waitingRoomInfoRepository.setHomeToastMessage(message)
    }

    fun setHomeToastMessageState(state: Boolean) {
        waitingRoomInfoRepository.setHomeToastMessageState(state)
    }
}
