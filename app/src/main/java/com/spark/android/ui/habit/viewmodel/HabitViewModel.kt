package com.spark.android.ui.habit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spark.android.data.remote.RetrofitBuilder
import com.spark.android.data.remote.entity.request.SendSparkRequest
import com.spark.android.data.remote.entity.request.SetStatusRequest
import com.spark.android.data.remote.entity.response.HabitRecord
import com.spark.android.data.remote.entity.response.HabitResponse
import com.spark.android.data.remote.service.HabitService
import com.spark.android.data.remote.service.SendSparkService
import com.spark.android.data.remote.service.SetStatusService
import com.spark.android.util.Event
import kotlinx.coroutines.launch

class HabitViewModel : ViewModel() {
    private val habitService: HabitService = RetrofitBuilder.habitService
    private val setStatusService: SetStatusService = RetrofitBuilder.setStatusService
    private val sendSparkService: SendSparkService = RetrofitBuilder.sendSparkService

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _habitInfo = MutableLiveData<HabitResponse>()
    val habitInfo: LiveData<HabitResponse> = _habitInfo

    private val _habitRecordList = MutableLiveData<MutableList<HabitRecord>>()
    val habitRecordList: LiveData<MutableList<HabitRecord>> = _habitRecordList

    private val _sendSuccess = MutableLiveData<Boolean>()
    val sendSuccess: LiveData<Boolean> = _sendSuccess

    fun initIsLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun initSendSuccess(success: Boolean) {
        _sendSuccess.value = success
    }

    fun getHabitRoomInfo(roomId: Int) {
        initIsLoading(true)
        viewModelScope.launch {
            kotlin.runCatching {
                habitService.getHabitRoom(roomId)
            }.onSuccess { response ->
                val data = response.data ?: throw NullPointerException("습관방 통신 에러")
                _habitInfo.postValue(data)

                _habitRecordList.postValue(mutableListOf<HabitRecord>().apply {
                    add(data.myRecord)
                    addAll(data.otherRecords.map {
                        HabitRecord(
                            nickname = it.nickname,
                            profileImg = it.profileImg,
                            recordId = it.recordId,
                            status = it.status,
                            userId = it.userId
                        )
                    })
                })
                initIsLoading(false)
            }.onFailure { }
        }
    }

    fun postStatus(statusType: String) {
        viewModelScope.launch {
            habitInfo.value?.let {
                setStatusService.setStatus(
                    it.roomId,
                    SetStatusRequest(statusType)
                )
            }
        }
    }

    fun postSendSpark(content: String, recordId: Int) {
        viewModelScope.launch {
            habitInfo.value?.let {
                kotlin.runCatching {
                    sendSparkService.sendSpark(
                        it.roomId,
                        SendSparkRequest(content, recordId)
                    )
                }.onSuccess { _sendSuccess.value = true }

            }
        }
    }
}
