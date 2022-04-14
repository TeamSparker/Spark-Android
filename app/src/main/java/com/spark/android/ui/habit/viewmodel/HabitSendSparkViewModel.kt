package com.spark.android.ui.habit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spark.android.data.remote.RetrofitBuilder
import com.spark.android.data.remote.entity.request.SendSparkRequest
import com.spark.android.data.remote.service.SendSparkService
import kotlinx.coroutines.launch

class HabitSendSparkViewModel : ViewModel() {
    private val sendSparkService: SendSparkService = RetrofitBuilder.sendSparkService

    private val _roomId = MutableLiveData<Int>()
    val roomId: LiveData<Int> = _roomId

    private val _recordId = MutableLiveData<Int>()
    val recordId: LiveData<Int> = _recordId

    private val _nickname = MutableLiveData<String>()
    val nickname: LiveData<String> = _nickname

    private val _profileImg = MutableLiveData<String>()
    val profileImg: LiveData<String> = _profileImg

    private val _isTyping = MutableLiveData<Boolean>()
    val isTyping: LiveData<Boolean> = _isTyping

    var message = MutableLiveData("")

    fun initRoomId(roomId: Int) {
        _roomId.value = roomId
    }

    fun initRecordId(recordId: Int) {
        _recordId.value = recordId
    }

    fun initNickname(nickname: String) {
        _nickname.value = nickname
    }

    fun initProfileImg(profileImg: String) {
        _profileImg.value = profileImg
    }

    fun initIsTyping(isTyping: Boolean) {
        _isTyping.value = isTyping
    }

    fun postSendSpark(content: String) {
        viewModelScope.launch {
            roomId.value?.let { roomId ->
                recordId.value?.let { recordId ->
                    sendSparkService.sendSpark(roomId, SendSparkRequest(content, recordId))
                }
            }
        }
    }
}