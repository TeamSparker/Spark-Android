package com.spark.android.ui.joincode.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spark.android.data.remote.repository.JoinCodeRoomDoneRepository
import com.spark.android.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class JoinCodeViewModel @Inject constructor(
    private val joinCodeRoomDoneRepository: JoinCodeRoomDoneRepository
) : ViewModel() {

    private val _joinCodeState = MutableLiveData<Event<Boolean>>()
    val joinCodeState: LiveData<Event<Boolean>> = _joinCodeState

    fun setJoinCodeRoomDone(roomId: Int) {
        viewModelScope.launch {
            joinCodeRoomDoneRepository.setJoinCodeRoomDone(roomId)
                .onSuccess {
                    _joinCodeState.postValue(Event(true))
                }
                .onFailure {
                    Timber.tag("joinCodeRoom").d(it.message.toString())
                }
        }
    }
}
