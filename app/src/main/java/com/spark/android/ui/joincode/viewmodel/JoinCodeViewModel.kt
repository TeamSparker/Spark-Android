package com.spark.android.ui.joincode.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spark.android.data.remote.repository.JoinCodeRoomDoneRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JoinCodeViewModel @Inject constructor(
    private val joinCodeRoomDoneRepository: JoinCodeRoomDoneRepository
):ViewModel() {

    fun setJoinCodeRoomDone(roomId:Int){
        viewModelScope.launch {
            joinCodeRoomDoneRepository.setJoinCodeRoomDone(roomId)
                .onFailure {
                    Log.d("joinCodeRoom",it.message.toString())
                }
        }
    }
}