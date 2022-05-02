package com.spark.android.ui.setpurpose.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spark.android.data.remote.entity.request.SetPurposeRequest
import com.spark.android.data.remote.repository.SetPurposeRepository
import com.spark.android.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SetPurposeViewModel @Inject constructor(
    private val setPurposeRepository: SetPurposeRepository
) : ViewModel() {

    var habitWhen = MutableLiveData("")
    var myPurpose = MutableLiveData("")
    var networkState = MutableLiveData<Event<Boolean>>()

    fun setPurpose(roomId: Int, body: SetPurposeRequest) {
        viewModelScope.launch {
            setPurposeRepository.setPurpose(roomId, body)
                .onSuccess {
                    networkState.postValue(Event(it.success))
                }.onFailure {
                    Timber.tag("setPurpose").d(it.message.toString())
                }
        }
    }

    fun setLastPurpose(moment: String, purpose: String) {
        habitWhen.postValue(moment)
        myPurpose.postValue(purpose)
    }
}
