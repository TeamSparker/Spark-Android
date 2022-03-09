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
    val isLoading :LiveData<Boolean> = _isLoading

    var lastId = -1
        private set

    fun updateIsLoading(){
        _isLoading.postValue(false)
    }

    fun getHomeAllRoom(lastid: Int, size: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            homeRepository.getHomeAllRoom(lastid,size)
                .onSuccess {
                    _roomList.postValue(it.data.rooms)
                }.onFailure {
                    Log.d("Home_main error", it.message.toString())
                }
        }
    }

    fun getHomeToastMessage() = homeRepository.getHomeToastMessage()

    fun getHomeToastMessageState() = homeRepository.getHomeToastMessageState()
}