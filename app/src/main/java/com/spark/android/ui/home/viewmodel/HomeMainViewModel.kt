package com.spark.android.ui.home.viewmodel

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

    fun getHomeAllRoom(lastid: Int, size: Int) {
        viewModelScope.launch {
            val response = homeRepository.getHomeAllRoom(lastid, size)
            _roomList.postValue(response.data?.rooms)
        }
    }
}