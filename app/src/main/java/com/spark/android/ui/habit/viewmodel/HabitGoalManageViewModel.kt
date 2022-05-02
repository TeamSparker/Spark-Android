package com.spark.android.ui.habit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spark.android.data.remote.RetrofitBuilder
import com.spark.android.data.remote.entity.request.SetPurposeRequest
import com.spark.android.data.remote.service.SetPurposeService
import kotlinx.coroutines.launch

class HabitGoalManageViewModel : ViewModel() {
    private val setPurposeService: SetPurposeService = RetrofitBuilder.setPurposeService
    private val _roomId = MutableLiveData<Int>()
    val roomId: LiveData<Int> = _roomId

    private val _roomName = MutableLiveData<String>()
    val roomName: LiveData<String> = _roomName

    var purpose = MutableLiveData("")
    var moment = MutableLiveData("")

    fun initRoomId(roomId: Int) {
        _roomId.value = roomId
    }

    fun initRoomName(roomName: String) {
        _roomName.value = roomName
    }

    fun setPurpose() {
        viewModelScope.launch {
            roomId.value?.let { roomId ->
                moment.value?.let { moment ->
                    purpose.value?.let { purpose ->
                        setPurposeService.setPurpose(
                            roomId,
                            SetPurposeRequest(moment, purpose)
                        )
                    }
                }
            }
        }
    }
}
