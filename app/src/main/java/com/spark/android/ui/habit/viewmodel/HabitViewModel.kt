package com.spark.android.ui.habit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spark.android.data.remote.RetrofitBuilder
import com.spark.android.data.remote.entity.request.SetStatusRequest
import com.spark.android.data.remote.entity.response.HabitRecord
import com.spark.android.data.remote.entity.response.HabitResponse
import com.spark.android.data.remote.service.HabitService
import com.spark.android.data.remote.service.SetStatusService
import kotlinx.coroutines.launch

class HabitViewModel : ViewModel() {
    private val habitService: HabitService = RetrofitBuilder.habitService
    private val setStatusService: SetStatusService = RetrofitBuilder.setStatusService

    private val _habitInfo = MutableLiveData<HabitResponse>()
    val habitInfo: LiveData<HabitResponse> = _habitInfo

    private val _habitRecordList = MutableLiveData<MutableList<HabitRecord>>()
    val habitRecordList: LiveData<MutableList<HabitRecord>> = _habitRecordList

    fun getHabitRoomInfo(roomId: Int) {
        viewModelScope.launch {
            val response = habitService.getHabitRoom(roomId)
            val data = response.data ?: throw NullPointerException("습관방 통신 에러")
            _habitInfo.postValue(data)

            _habitRecordList.postValue(mutableListOf<HabitRecord>().apply {
                add(data.myRecord)
                addAll(data.otherRecords.map {
                    HabitRecord(nickname = it.nickname,
                        profileImg = it.profileImg,
                        recordId = it.recordId,
                        status = it.status,
                        userId = it.userId)
                })
            })
        }
    }

    fun postStatus(statusType: String) {
        viewModelScope.launch {
            habitInfo.value?.let {
                setStatusService.setStatus(it.roomId,
                    SetStatusRequest(statusType))
            }
        }
    }
}