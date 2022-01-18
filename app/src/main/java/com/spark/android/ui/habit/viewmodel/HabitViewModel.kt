package com.spark.android.ui.habit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spark.android.data.remote.RetrofitBuilder
import com.spark.android.data.remote.entity.response.HabitResponse
import com.spark.android.data.remote.service.HabitService
import kotlinx.coroutines.launch

class HabitViewModel : ViewModel() {
    private val habitService: HabitService = RetrofitBuilder.habitService

    private val _habitInfo = MutableLiveData<HabitResponse>()
    val habitInfo: LiveData<HabitResponse> = _habitInfo

    fun getHabitRoomInfo(roomId: Int) {
        viewModelScope.launch {
            val response = habitService.getHabitRoom(roomId)
            _habitInfo.postValue(response.data!!)
        }
    }
}