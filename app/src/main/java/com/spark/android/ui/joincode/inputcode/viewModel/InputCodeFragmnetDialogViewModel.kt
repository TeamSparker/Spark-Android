package com.spark.android.ui.joincode.inputcode.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spark.android.data.remote.entity.response.JoinCodeRoomInfoResponse
import com.spark.android.data.remote.repository.JoinCodeRoomInfoRepository
import com.spark.android.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class InputCodeFragmentDialogViewModel @Inject constructor(
    private val joinCodeRoomInfoRepository: JoinCodeRoomInfoRepository
) : ViewModel() {
    val roomCode = MutableLiveData<String>("")

    private var _errorMessage = MutableLiveData<String>("")
    val errorMessage: LiveData<String> = _errorMessage

    private var _roomInfo = MutableLiveData<Event<JoinCodeRoomInfoResponse>>()
    val roomInfo: LiveData<Event<JoinCodeRoomInfoResponse>> = _roomInfo

    fun clearErrorMessage() {
        _errorMessage.postValue("")
    }

    fun getJoinCodeRoomInfo(code: String) {
        viewModelScope.launch {
            try {
                val response = joinCodeRoomInfoRepository.getJoinCodeRoomInfo(code)
                _roomInfo.postValue(Event(response.data!!))
            } catch (e: HttpException) {
                val rawData = e.response()?.errorBody()?.byteString().toString()
                val processedData = rawData.slice(IntRange(47, rawData.length - 4))
                _errorMessage.postValue(processedData)
            }
        }
    }
}
