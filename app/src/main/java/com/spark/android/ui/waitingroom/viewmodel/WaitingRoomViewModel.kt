package com.spark.android.ui.waitingroom.viewmodel

import android.util.Log
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
import kotlin.math.log

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
    val refreshInfo :LiveData<List<Member>> = _refreshInfo

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
                    Log.d("WaitingRoomInfo",it.message.toString())
                }
        }
    }

    fun getRefreshInfo(roomId: Int){
        viewModelScope.launch {
            refreshRepository.getRefresh(roomId)
                .onSuccess {
                    _refreshInfo.postValue(it.data.members)
                }.onFailure {
                    Log.d("refreshPeopleList",it.message.toString())
                }
        }
    }

    fun startHabit(roomId: Int){
        viewModelScope.launch {
            startHabitRepository.startHabit(roomId)
                .onFailure {
                    Log.d("startHabit",it.message.toString())
                }
        }
    }

}
