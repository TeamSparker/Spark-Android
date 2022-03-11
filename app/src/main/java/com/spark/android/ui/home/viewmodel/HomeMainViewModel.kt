package com.spark.android.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spark.android.data.remote.entity.response.Room
import com.spark.android.data.remote.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeMainViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _roomList = MutableLiveData<List<Room>>()
    val roomList: LiveData<List<Room>> = _roomList

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toastMessage = MutableLiveData("")
    val toastMessage: LiveData<String> = _toastMessage


    var lastId = -1
        private set

    fun updateIsLoading() {
        _isLoading.postValue(false)
    }

    fun getHomeAllRoom(lastid: Int, size: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            homeRepository.getHomeAllRoom(lastid, size)
                .onSuccess {
                    _roomList.postValue(it.data.rooms)
                }.onFailure {
                    Log.d("Home_main_error_get_list", it.message.toString())
                }
        }
    }

    fun readFinishHabitRoom(roomId: Int) {
        viewModelScope.launch {
            homeRepository.readFinishHabitRoom(roomId)
                .onFailure { Log.d("Home_main_error_finish_room", it.message.toString()) }
        }
    }

    fun updateToastMessage(message: String) {
        _toastMessage.postValue(message)
    }

    fun getHomeToastMessage(): String = homeRepository.getHomeToastMessage()

    fun getHomeToastMessageState(): Boolean = homeRepository.getHomeToastMessageState()

    fun setHomeToastMessage(message: String) {
        homeRepository.setHomeToastMessage(message)
    }

    fun setHomeToastMessageState(state: Boolean) {
        homeRepository.setHomeToastMessageState(state)
    }
}