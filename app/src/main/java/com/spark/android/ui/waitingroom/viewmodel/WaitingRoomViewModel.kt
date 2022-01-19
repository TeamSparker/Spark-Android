package com.spark.android.ui.waitingroom.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spark.android.data.remote.entity.response.Member
import com.spark.android.data.remote.entity.response.WaitingRoomInfoResponse
import com.spark.android.data.remote.repository.RefreshRepository
import com.spark.android.data.remote.repository.StartHabitRepository
import com.spark.android.data.remote.repository.WaitingRoomInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WaitingRoomViewModel @Inject constructor(
    private val waitingRoomInfoRepository: WaitingRoomInfoRepository,
    private val refreshRepository: RefreshRepository,
    private val startHabitRepository: StartHabitRepository
) : ViewModel() {

    private val _waitingRoomInfo = MutableLiveData<WaitingRoomInfoResponse>()
    val waitingRoomInfo: LiveData<WaitingRoomInfoResponse> = _waitingRoomInfo

    private val _refreshInfo = MutableLiveData<List<Member>>()
    val refreshInfo :LiveData<List<Member>> = _refreshInfo

    fun getWaitingRoomInfo(roomId: Int) {
        viewModelScope.launch {
            val response = waitingRoomInfoRepository.getWaitingRoomInfo(roomId)
            _waitingRoomInfo.postValue(response.data!!)
        }
    }

    fun getRefreshInfo(roomId: Int){
        viewModelScope.launch {
            val response = refreshRepository.getRefresh(roomId)
            _refreshInfo.postValue(response.data?.members)
        }
    }

    fun startHabit(roomId: Int){
        viewModelScope.launch {
            startHabitRepository.startHabit(roomId)
        }
    }

}