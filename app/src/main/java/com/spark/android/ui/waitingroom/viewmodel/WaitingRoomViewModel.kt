package com.spark.android.ui.waitingroom.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spark.android.data.remote.entity.response.WaitingRoomInfoResponse
import com.spark.android.data.remote.repository.WaitingRoomInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WaitingRoomViewModel @Inject constructor(
    private val waitingRoomInfoRepository: WaitingRoomInfoRepository
) : ViewModel() {

    private val _waitingRoomInfo = MutableLiveData<WaitingRoomInfoResponse>()
    val waitingRoomInfo: LiveData<WaitingRoomInfoResponse> = _waitingRoomInfo

    fun getWaitingRoomInfo(roomId: Int) {
        viewModelScope.launch {
            val response = waitingRoomInfoRepository.getWaitingRoomInfo(roomId)
            _waitingRoomInfo.postValue(response.data!!)
        }
    }

}