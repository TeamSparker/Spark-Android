package com.spark.android.ui.makeroom.selectconfirmmethod.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spark.android.data.remote.entity.request.MakeRoomRequest
import com.spark.android.data.remote.repository.MakeRoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class SelectConfirmMethodViewModel @Inject constructor(
    private val makeRoomRepository: MakeRoomRepository
) : ViewModel() {

    private val _methodState = MutableLiveData(METHOD_PICTURE)
    val methodState :LiveData<Boolean> = _methodState

    private val _roomId = MutableLiveData<Int>()
    val roomId :LiveData<Int> = _roomId

    fun selectMethodPicture(){
        _methodState.value = METHOD_PICTURE
    }

    fun selectMethodTimer(){
        _methodState.value = METHOD_TIMER
    }

    fun makeRoom(requestData : MakeRoomRequest) {
        viewModelScope.launch {
            makeRoomRepository.makeRoom(requestData)
                .onSuccess {
                    _roomId.postValue(it.data.roomId)
                }.onFailure {
                    Log.d("Select Confirm Method" , it.message.toString())
                }
        }
    }

    companion object{
        const val METHOD_PICTURE = false
        const val METHOD_TIMER = true
    }
}