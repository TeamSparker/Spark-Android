package com.spark.android.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spark.android.data.remote.entity.response.Room
import com.spark.android.data.remote.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeMainViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    init {
        getHomeNoticeRedDot()
    }

    private val _roomList = MutableLiveData(listOf<Room>())
    val roomList: LiveData<List<Room>> = _roomList

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toastMessage = MutableLiveData("")
    val toastMessage: LiveData<String> = _toastMessage

    private val _noticeRedDot = MutableLiveData(false)
    val noticeRedDot: LiveData<Boolean> = _noticeRedDot

    var lastId = -1
        private set

    var hasNextPage = true
        private set

    var isAddLoading = false
        private set

    var isRefresh = false
        private set

    private val loadingItem = Room(
        doneMemberNum = -1,
        myStatus = null,
        isStarted = false,
        leftDay = -1,
        life = -1,
        memberNum = -1,
        profileImg = null,
        roomId = -1,
        roomName = "로딩용 더미 방 데이터",
        isUploaded = true,
        infiniteLoading = "loading"
    )

    fun updateIsLoading(isLoading: Boolean) {
        _isLoading.postValue(isLoading)
    }

    fun recoverHomeAllRoom(size: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            homeRepository.getHomeAllRoom(-1, size)
                .onSuccess {
                    _roomList.postValue(it.data.rooms)
                }.onFailure {
                    Timber.tag("Home_main_error_get_list").d(it.message.toString())
                }
        }
    }

    fun getHomeAllRoom() {
        if (requireNotNull(roomList.value).isEmpty()) {
            updateIsLoading(true)
        }
        if (requireNotNull(roomList.value).isNotEmpty() && hasNextPage) {
            isAddLoading = true
            addLoadingItem()
        }
        viewModelScope.launch {
            homeRepository.getHomeAllRoom(lastId, LIST_LIMIT)
                .onSuccess { response ->
                    isRefresh = false
                    val tempHomeList = response.data.rooms
                    if (tempHomeList.isNotEmpty()) {
                        lastId = tempHomeList.last().roomId
                    }
                    if (tempHomeList.size < LIST_LIMIT && lastId != -1) {
                        hasNextPage = false
                    }
                    isAddLoading = false
                    updateIsLoading(false)
                    _roomList.postValue(
                        requireNotNull(_roomList.value).toMutableList().apply {
                            remove(loadingItem)
                            addAll(tempHomeList)
                        }
                    )
                }.onFailure {
                    Timber.tag("Home_GetHomeAllRoom").d(it.message.toString())
                }
        }
    }

    private fun addLoadingItem() {
        _roomList.value = requireNotNull(_roomList.value).toMutableList().apply { add(loadingItem) }
    }


    fun readFinishHabitRoom(roomId: Int) {
        viewModelScope.launch {
            homeRepository.readFinishHabitRoom(roomId)
                .onFailure { Timber.tag("Home_main_error_finish_room").d(it.message.toString()) }
        }
    }

    private fun getHomeNoticeRedDot() {
        viewModelScope.launch {
            homeRepository.getHomeNoticeRedDot()
                .onSuccess {
                    _noticeRedDot.postValue(it.data.newNotice)
                }
                .onFailure {
                    Timber.tag("Home_main_error_get_home_notice_red_dot").d(it.message.toString())
                }
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

    fun canGetNewRooms() = !isAddLoading && !isRefresh

    fun refreshHomeRoom() {
        lastId = -1
        hasNextPage = true
        isRefresh = true
        _roomList.value = mutableListOf()
        getHomeAllRoom()
    }

    companion object {
        private const val LIST_LIMIT = 6
    }
}
